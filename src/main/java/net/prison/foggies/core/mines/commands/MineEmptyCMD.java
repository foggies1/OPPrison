package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.MineStorage;
import net.prison.foggies.core.mines.PersonalMine;
import net.prison.foggies.core.utils.Permissions;
import org.bukkit.entity.Player;

public class MineEmptyCMD {

    public MineEmptyCMD(OPPrison plugin) {
        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .assertPermission(Permissions.MINE_EMPTY.getPermission())
                .handler(c -> {
                    final Player player = c.sender();
                    mineStorage.get(player.getLocation())
                            .whenComplete((mine, throwable) -> {

                                if (throwable != null) {
                                    throwable.printStackTrace();
                                    return;
                                }

                                mine.ifPresent(PersonalMine::empty);

                            });
                })
                .register("mineempty");

    }
}
