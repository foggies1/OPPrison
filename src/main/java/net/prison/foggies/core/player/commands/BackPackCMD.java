package net.prison.foggies.core.player.commands;

import me.lucko.helper.Commands;
import net.milkbowl.vault.economy.Economy;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.player.ui.BackPackUI;

public class BackPackCMD {

    public BackPackCMD(OPPrison plugin){
        final PlayerStorage playerStorage = plugin.getPlayerStorage();
        final Economy economy = plugin.getEconomy();

        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    playerStorage.get(c.sender().getUniqueId())
                            .ifPresent(pp -> new BackPackUI(c.sender(), pp.getBackPack().getContents(c.sender(), economy)).open());
                })
                .register("backpack", "bp");
    }

}
