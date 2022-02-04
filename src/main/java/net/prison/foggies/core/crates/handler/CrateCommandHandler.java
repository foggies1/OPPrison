package net.prison.foggies.core.crates.handler;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.commands.CrateCMD;

public class CrateCommandHandler {

    public CrateCommandHandler(OPPrison plugin) {
        new CrateCMD(plugin);
    }
}
