package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.ui.MinePanelUI;
import org.bukkit.OfflinePlayer;

public class MinePanelCMD {

    public MinePanelCMD(OPPrison plugin){
        Commands.create()
                .assertPlayer()
                .assertUsage("<player>")
                .handler(c -> {
                    final OfflinePlayer target = c.arg(0).parseOrFail(OfflinePlayer.class);
                    new MinePanelUI(c.sender(), target, plugin).open();
                })
                .register("panel");
    }

}
