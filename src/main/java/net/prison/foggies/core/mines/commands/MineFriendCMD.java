package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.storage.MineStorage;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.OfflinePlayer;

public class MineFriendCMD {

    public MineFriendCMD(OPPrison plugin) {
        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .assertUsage("<add,remove> <player>")
                .handler(c -> {
                    final OfflinePlayer target = c.arg(1).parseOrFail(OfflinePlayer.class);
                    String action = c.arg(0).parseOrFail(String.class);

                    mineStorage.get(c.sender().getUniqueId()).ifPresent(personalMine -> {
                        switch (action.toLowerCase()) {
                            case "add" -> {
                                personalMine.giveAccess(target.getUniqueId());
                                Players.msg(c.sender(), Lang.MINE_ADDED_FRIEND.getMessage());
                            }
                            case "remove" -> {
                                personalMine.removeAccess(target.getUniqueId());
                                Players.msg(c.sender(), Lang.MINE_REMOVED_FRIEND.getMessage());
                            }
                        }
                    });

                })
                .register("minefriend", "mfriend", "mf");
    }
}
