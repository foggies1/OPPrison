package net.prison.foggies.core.player.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.MineStorage;
import net.prison.foggies.core.mines.PersonalMine;
import net.prison.foggies.core.player.PlayerStorage;
import net.prison.foggies.core.player.ui.PlayerDataUI;
import net.prison.foggies.core.utils.Permissions;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PlayerDataCMD {

    public PlayerDataCMD(final OPPrison plugin){
        final PlayerStorage playerStorage = plugin.getPlayerStorage();
        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .assertPermission(Permissions.PLAYER_DATA.getPermission())
                .assertUsage("<player>")
                .handler(c -> {
                    Player target = c.arg(0).parseOrFail(Player.class);

                    Optional<PersonalMine> personalMine = mineStorage.get(target.getUniqueId());
                    if(personalMine.isEmpty()) return;


                    playerStorage.get(target.getUniqueId())
                            .whenComplete(((optionalPrisonPlayer, throwable) -> {
                                if(throwable != null){
                                    throwable.printStackTrace();
                                    return;
                                }

                                optionalPrisonPlayer.ifPresent(p -> new PlayerDataUI(personalMine.get(), p, c.sender()).open());

                            }));


                })
                .register("playerdata", "pd");

    }

}
