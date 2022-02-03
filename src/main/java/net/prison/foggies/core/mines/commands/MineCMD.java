package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.storage.MineStorage;
import net.prison.foggies.core.mines.ui.MinePanelUI;

public class MineCMD {

    public MineCMD(OPPrison plugin) {

        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    if (mineStorage.get(c.sender().getUniqueId()).isEmpty())
                        mineStorage.createMine(c.sender().getUniqueId());
                    else
                        new MinePanelUI(c.sender(), c.sender(), plugin).open();
                })
                .register("mine", "pmine");

    }
}
