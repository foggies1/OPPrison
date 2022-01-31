package net.prison.foggies.core.player.commands;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.commands.level.*;
import net.prison.foggies.core.player.commands.prestige.*;

public class PlayerCommandHandler {

    public PlayerCommandHandler(OPPrison plugin) {
        new AddPrestigeCMD(plugin);
        new PrestigeCMD(plugin);
        new SetPrestigeCMD(plugin);
        new TakePrestigeCMD(plugin);
        new PrestigeMaxCMD(plugin);

        new AddExperienceCMD(plugin);
        new SetExperienceCMD(plugin);
        new TakeLevelCMD(plugin);

        new AddLevelCMD(plugin);
        new SetLevelCMD(plugin);
        new TakeLevelCMD(plugin);

        new PlayerDataCMD(plugin);
    }
}
