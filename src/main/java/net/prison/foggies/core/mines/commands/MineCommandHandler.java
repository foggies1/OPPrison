package net.prison.foggies.core.mines.commands;

import net.prison.foggies.core.OPPrison;

public class MineCommandHandler {

    public MineCommandHandler(OPPrison plugin) {
        new MineCMD(plugin);
        new MineTeleportCMD(plugin);
        new MineIncreaseCMD(plugin);
        new MineResetCMD(plugin);
        new MinePanelCMD(plugin);
        new MineFriendCMD(plugin);
    }
}
