package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.handler.MineQueueHandler;
import net.prison.foggies.core.mines.storage.MineStorage;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.entity.Player;

public class MineResetCMD {

    public MineResetCMD(OPPrison plugin) {
        final MineQueueHandler mineQueueHandler = plugin.getMineQueueHandler();
        final MineStorage mineStorage = plugin.getMineStorage();
        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    final Player player = c.sender();
                    mineStorage.get(player.getUniqueId()).ifPresent(mine -> {
                        if(mineQueueHandler.addToQueue(mine))
                            Players.msg(player, Lang.MINE_ADDED_TO_QUEUE.getMessage());
                        else
                            Players.msg(player, Lang.MINE_ALREADY_IN_QUEUE.getMessage());
                    });
                })
                .register("rm");
    }
}
