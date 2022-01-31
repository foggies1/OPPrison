package net.prison.foggies.core.pickaxe;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.commands.PickaxeGiveCMD;

public class PickaxeCommandHandler {

    public PickaxeCommandHandler(OPPrison plugin) {
        new PickaxeGiveCMD(plugin);
    }
}
