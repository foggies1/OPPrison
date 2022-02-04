package net.prison.foggies.core.crates.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.storage.CrateStorage;
import net.prison.foggies.core.crates.ui.CrateUI;

public class CrateCMD {

    public CrateCMD(OPPrison plugin) {
        final CrateStorage crateStorage = plugin.getCrateStorage();
        Commands.create()
                .assertPlayer()
                .assertUsage("<type>")
                .handler(c -> {
                    String type = c.arg(0).parseOrFail(String.class);
                    crateStorage.getCrate(type).ifPresent(crate -> {
                        new CrateUI(plugin, c.sender(), crate).open();
                    });
                })
                .register("crate");
    }
}
