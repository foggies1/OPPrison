package net.prison.foggies.core.mines.handler;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.commands.*;

public class MineCommandHandler {

    public MineCommandHandler(OPPrison plugin) {
        new MineCMD(plugin);
        new MineTeleportCMD(plugin);
        new MineIncreaseCMD(plugin);
        new MineResetCMD(plugin);
        new MinePanelCMD(plugin);
        new MineFriendCMD(plugin);
        new FrenzyTokenGiveCMD(plugin);
    }
}
