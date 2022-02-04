package net.prison.foggies.core.mines.luckyblock;

import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.luckyblock.constant.LuckyType;
import net.prison.foggies.core.mines.model.LuckyBlockReward;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class MediumTokenReward extends LuckyBlockReward {

    @Override
    public void applyReward(OPPrison plugin, Player player) {
        final PlayerStorage playerStorage = plugin.getPlayerStorage();
        Optional<PrisonPlayer> prisonPlayer = playerStorage.get(player.getUniqueId());

        long tokens = ThreadLocalRandom.current().nextLong(100000, 500000);
        prisonPlayer.ifPresent(pp -> {
            pp.addTokens(tokens, false);
            Players.msg(player, Lang.TOKEN_REWARD.getMessage()
                    .replace("%tokens%", Number.pretty(tokens))
                    .replace("%luck_type%", getLuckyType().getDisplayName())
            );
        });
     }

    @Override
    public double getWeight() {
        return 1;
    }

    @Override
    public LuckyType getLuckyType() {
        return LuckyType.VERY;
    }

}
