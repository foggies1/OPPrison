package net.prison.foggies.core.crates.obj;

import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.random.Weighted;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Setter
@Getter
public class CrateReward implements Weighted {

    private final ItemStack itemStack;
    private final String displayName;
    private final Material material;
    private final List<String> lore;
    private final List<String> rewards;
    private final double chance;

    public CrateReward(ConfigurationSection rewardSection){
        this.displayName = rewardSection.getString("display-name");
        this.material = Material.valueOf(rewardSection.getString("material"));
        this.lore = rewardSection.getStringList("lore");
        this.rewards = rewardSection.getStringList("reward-list");
        this.chance = rewardSection.getDouble("chance");

        this.itemStack = ItemStackBuilder
                .of(material)
                .name(displayName)
                .lore(lore)
                .enchant(Enchantment.DIG_SPEED).hideAttributes()
                .build();
    }

    @Override
    public double getWeight() {
        return chance;
    }
}
