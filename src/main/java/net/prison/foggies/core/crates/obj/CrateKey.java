package net.prison.foggies.core.crates.obj;

import lombok.Getter;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class CrateKey {

    private final ItemStack itemStack;
    private final Material material;
    private final String name;
    private final List<String> lore;

    public CrateKey(ConfigurationSection item){
        this.material = Material.valueOf(item.getString("material"));
        this.name = item.getString("display-name");
        this.lore = item.getStringList("lore");

        this.itemStack = ItemStackBuilder
                .of(this.material)
                .name(this.name)
                .lore(this.lore)
                .enchant(Enchantment.DIG_SPEED).hideAttributes()
                .build();
    }

}
