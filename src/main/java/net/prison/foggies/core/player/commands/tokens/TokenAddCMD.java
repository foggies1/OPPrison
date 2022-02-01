package net.prison.foggies.core.player.commands.tokens;

import me.lucko.helper.Commands;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Permissions;
import org.bukkit.entity.Player;

public class TokenAddCMD {

    public TokenAddCMD(OPPrison plugin){
        final PlayerStorage playerStorage = plugin.getPlayerStorage();

        Commands.create()
                .assertPermission(Permissions.TOKEN_ADMIN.getPermission())
                .assertUsage("<player> <amount>")
                .handler(c -> {
                    Player target = c.arg(0).parseOrFail(Player.class);
                    long amount = c.arg(1).parseOrFail(Long.class);

                    if(amount < 0){
                        Players.msg(c.sender(), Lang.GREATER_THAN_0.getMessage());
                        return;
                    }

                    playerStorage.get(target.getUniqueId())
                            .whenCompleteAsync((prisonPlayer, throwable) -> {
                                if(throwable != null){
                                    throwable.printStackTrace();
                                    return;
                                }

                                prisonPlayer.ifPresent(pp -> pp.addTokens(amount, true));

                            });

                    // TODO: Implement message to target and sender.
                })
                .register("tokengive", "tokenadd", "teadd", "tadd", "tgive");
    }

}