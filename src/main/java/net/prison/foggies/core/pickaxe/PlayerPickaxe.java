package net.prison.foggies.core.pickaxe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.utils.Number;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
@AllArgsConstructor
public class PlayerPickaxe {

    private UUID uuid;
    private HashMap<EnchantBase, Long> enchantments; // can you serialize a class than extends an abstract class?

    public PlayerPickaxe(EnchantHandler enchantHandler, UUID uuid) {
        this.uuid = uuid;

        HashMap<EnchantBase, Long> enchantments = new HashMap<>();
        enchantHandler.getEnchantMap()
                .values()
                .forEach(enchant -> enchantments.put(enchant, enchant.getStartLevel()));

        this.enchantments = enchantments;
    }

    public ItemStack toItemStack(){
        ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                .name("&aUltimate Pickaxe")
                .enchant(Enchantment.DIG_SPEED, 100)
                .hideAttributes()
                .amount(1);

        List<String> lore = new ArrayList<>();
        enchantments.forEach((enchant, level) -> {
            if(level > 0)
                lore.add(enchant.getDisplayName()  + " " + Number.pretty(level));
        });

        lore.add("&7-----------------------");
        lore.add("");
        lore.add("&7-----------------------");

        return itemStackBuilder.lore(lore).build();
    }

    public void addLevel(EnchantHandler enchantHandler, String identifier, long amount) {
        Optional<EnchantBase> enchantBase = enchantHandler.getEnchant(identifier);
        long currentLevel = getLevel(identifier);
        if(currentLevel == -1L || enchantBase.isEmpty()) return;

        long maxLevel = enchantBase.get().getMaxLevel();
        if(currentLevel + amount > maxLevel) amount = maxLevel - currentLevel;

        setLevel(enchantHandler, identifier, currentLevel + amount);
        notifyUpgrade(enchantBase.get(), amount);
    }

    public void setLevel(EnchantHandler enchantHandler, String identifier, long amount) {
        long currentLevel = getLevel(identifier);
        if (currentLevel == -1) return;

        Optional<EnchantBase> enchantBase = getEnchant(identifier);
        if (enchantBase.isPresent()) {
            enchantments.replace(enchantBase.get(), amount);
            notifyUpgrade(enchantBase.get(), amount);
        } else {
            addEnchantment(enchantHandler, identifier);
        }
    }

    public void addEnchantment(EnchantHandler handler, String identifier) {
        Optional<EnchantBase> enchantBase = handler.getEnchant(identifier);
        enchantBase.ifPresent(base -> enchantments.putIfAbsent(base, 0L));
    }

    public long getLevel(String identifier) {
        Optional<EnchantBase> enchantBase = getEnchant(identifier);
        if (enchantBase.isEmpty()) return -1;
        return enchantments.get(enchantBase.get());
    }

    public Optional<EnchantBase> getEnchant(String identifier) {
        return enchantments.keySet().stream().filter(enchant -> enchant.getIdentifier().equalsIgnoreCase(identifier)).findFirst();
    }

    private void notifyUpgrade(EnchantBase enchant, long amount){
        Players.msg(toBukkit(), "&aYou've upgraded " + enchant.getDisplayName() + " by x" + Number.pretty(amount));
    }

    private Player toBukkit(){
        return Bukkit.getPlayer(uuid);
    }

}
