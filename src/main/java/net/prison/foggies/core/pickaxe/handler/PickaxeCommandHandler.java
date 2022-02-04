package net.prison.foggies.core.pickaxe.handler;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.commands.SettingsCMD;

public class PickaxeCommandHandler {

    public PickaxeCommandHandler(OPPrison plugin) {
        new SettingsCMD(plugin);
    }
}
