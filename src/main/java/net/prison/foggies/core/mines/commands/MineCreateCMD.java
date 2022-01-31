package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.MineStorage;

public class MineCreateCMD {

    public MineCreateCMD(OPPrison plugin) {

        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .assertUsage("<create>")
                .handler(c -> {
                    mineStorage.createMine(c.sender().getUniqueId());
                })
                .register("mine");

    }
}
