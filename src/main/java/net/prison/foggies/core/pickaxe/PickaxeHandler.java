package net.prison.foggies.core.pickaxe;

import me.lucko.helper.Schedulers;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.utils.Players;
import net.kyori.adventure.text.Component;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.utils.PersistentData;
import net.prison.foggies.core.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PickaxeHandler {

    private final OPPrison plugin;
    private final EnchantHandler enchantHandler;
    private static final String PICKAXE_KEY = "PICKAXE_ITEM";

    public PickaxeHandler(OPPrison plugin) {
        this.plugin = plugin;
        this.enchantHandler = plugin.getEnchantHandler();
        Schedulers.async()
                .runRepeating(() ->
                        Players.forEach(this::updateLore),
                        20L, 20L * 10L);
    }

    public ItemStack createNewPickaxe() {

        ItemStack itemStack = ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                .enchant(Enchantment.DIG_SPEED, 100)
                .hideAttributes()
                .amount(1)
                .name("&aPickaxe")
                .build();

        new PersistentData(PICKAXE_KEY, itemStack).setString("yes");
        applyEnchants(itemStack);
        itemStack = updateLore(itemStack);

        return itemStack;
    }

    public void updateLore(Player player){
        Optional<ItemStack> pickaxe = getPickaxe(player);
        pickaxe.ifPresent(pick -> player.getInventory().setItem(0, updateLore(pick)));
    }

    public Optional<ItemStack> getPickaxe(Player player){
        ItemStack pickaxe = player.getInventory().getItem(0);
        return isPickaxeItem(pickaxe) ? Optional.ofNullable(pickaxe) : Optional.empty();
    }

    public ItemStack updateLore(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> lore = new ArrayList<>();

        new PersistentData(itemStack).getKeys().forEach(key -> System.out.println(key.getKey()));

        new PersistentData(itemStack)
                .getKeys()
                .forEach(key -> {
                    enchantHandler.getEnchant(key.getKey().toUpperCase()).ifPresent(enchant -> {
                        lore.add(Component.text(StringUtils.color(enchant.getDisplayName() + " " + getEnchantLevel(key.getKey(), itemStack))));
                    });
                });

        lore.add(Component.text(StringUtils.color("&7&m------------------------")));
        lore.add(Component.text(""));
        lore.add(Component.text(StringUtils.color("&7&m------------------------")));

        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void addEnchantLevel(String enchant, Player player, long amount) {
        Optional<ItemStack> pickaxe = getPickaxe(player);
        pickaxe.ifPresent(pick -> setEnchantLevel(enchant, player, getEnchantLevel(enchant, player) + amount));
    }

    public void setEnchantLevel(String enchant, Player player, long amount) {
        Optional<ItemStack> pickaxe = getPickaxe(player);
        pickaxe.ifPresent(pick -> {
            new PersistentData(enchant, pick).setLong(amount);
        });
    }

    public long getEnchantLevel(String enchant, Player player) {
        Optional<ItemStack> pickaxe = getPickaxe(player);
        if(pickaxe.isEmpty()) return -1;
        return new PersistentData(enchant, pickaxe.get()).getLong();
    }

    public long getEnchantLevel(String enchant, ItemStack itemStack) {
        if(itemStack == null || itemStack.getType() == Material.AIR || !isPickaxeItem(itemStack)) return -1;
        return new PersistentData(enchant, itemStack).getLong();
    }

    private void applyEnchants(ItemStack itemStack) {
        enchantHandler.getEnchantMap()
                .values()
                .stream()
                .filter(enchant -> enchant.getStartLevel() > 0)
                .forEach(enchant -> new PersistentData(enchant.getIdentifier(), itemStack).setLong(enchant.getStartLevel()));
    }

    public boolean isPickaxeItem(ItemStack itemStack) {
        return new PersistentData(PICKAXE_KEY, itemStack).containsKey();
    }

}
