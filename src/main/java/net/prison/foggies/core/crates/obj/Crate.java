package net.prison.foggies.core.crates.obj;

import lombok.Getter;
import me.lucko.helper.random.RandomSelector;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Crate {

    private final String displayName;
    private final String identifier;
    private final List<CrateReward> crateRewardList;
    private final CrateKey crateKey;

    public Crate(ConfigurationSection crateSection, String identifier) {

        this.displayName = crateSection.getString("menu-name");
        this.identifier = identifier;

        List<CrateReward> rewards = new ArrayList<>();
        final ConfigurationSection rewardSection = crateSection.getConfigurationSection("rewards");
        final ConfigurationSection keyItemSection = crateSection.getConfigurationSection("key-item");

        rewardSection.getKeys(false).forEach(reward -> {
            ConfigurationSection section = rewardSection.getConfigurationSection(reward);
            rewards.add(new CrateReward(section));
        });

        this.crateRewardList = rewards;
        this.crateKey = new CrateKey(keyItemSection);

    }

    public CrateReward getReward(){
        return RandomSelector.weighted(getCrateRewardList()).pick();
    }

}
