package net.prison.foggies.core.mines.commands;

import net.prison.foggies.core.OPPrison;

public class MineCommandHandler {

    public MineCommandHandler(OPPrison plugin) {
        new MineCreateCMD(plugin);
        new MineTeleportCMD(plugin);
        new MineIncreaseCMD(plugin);
        new MineBlockCMD(plugin);
        new MineEmptyCMD(plugin);
        new MineResetCMD(plugin);
        new MinePanelCMD(plugin);
    }
}
