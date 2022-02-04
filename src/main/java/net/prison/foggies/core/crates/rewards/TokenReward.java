package net.prison.foggies.core.crates.rewards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.model.ParsedReward;
import net.prison.foggies.core.player.storage.PlayerStorage;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
@Setter
public class TokenReward extends ParsedReward {

    private final OPPrison plugin;
    private final PlayerStorage playerStorage;
    private final long tokenAmount;

    @Override
    public void apply(Player player) {
        playerStorage.get(player.getUniqueId()).ifPresent(prisonPlayer -> {
            prisonPlayer.addTokens(tokenAmount, false);
            Players.msg(player, "Received " + tokenAmount + " tokens....");
        });
    }

}
