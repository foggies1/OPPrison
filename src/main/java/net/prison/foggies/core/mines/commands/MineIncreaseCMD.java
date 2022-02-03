package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.storage.MineStorage;

public class MineIncreaseCMD {

    public MineIncreaseCMD(OPPrison plugin) {
        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .assertUsage("<size>")
                .handler(c -> {

                    int increaseSize = c.arg(0).parseOrFail(Integer.class);

                    mineStorage.get(c.sender().getUniqueId()).ifPresent(mine -> {
                                mine.getMineRegion().increaseMineRegion(increaseSize);
                                mine.reset();
                            }
                    );
                })
                .register("minesize");

    }
}
