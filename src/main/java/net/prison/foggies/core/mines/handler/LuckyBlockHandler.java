package net.prison.foggies.core.mines.handler;

import me.lucko.helper.random.RandomSelector;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.luckyblock.LargeTokenReward;
import net.prison.foggies.core.mines.luckyblock.MediumTokenReward;
import net.prison.foggies.core.mines.luckyblock.SmallTokenReward;
import net.prison.foggies.core.mines.model.LuckyBlockReward;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LuckyBlockHandler {

    private final OPPrison plugin;
    private final List<LuckyBlockReward> rewards;

    public LuckyBlockHandler(OPPrison plugin) {
        this.plugin = plugin;
        this.rewards = getRewards();
    }

    public void getAndApply(Player player) {
        pickRandom().applyReward(plugin, player);
    }

    private LuckyBlockReward pickRandom() {
        return RandomSelector.weighted(this.rewards).pick();
    }

    private List<LuckyBlockReward> getRewards() {
        return new ArrayList<>(
                Arrays.asList(
                        new LargeTokenReward(),
                        new MediumTokenReward(),
                        new SmallTokenReward()
                )
        );
    }

}
