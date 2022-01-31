package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.ui.MineBlockUI;

public class MineBlockCMD {

    public MineBlockCMD(OPPrison plugin) {
        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    new MineBlockUI(plugin, c.sender()).open();
                })
                .register("mineblock");
    }
}
