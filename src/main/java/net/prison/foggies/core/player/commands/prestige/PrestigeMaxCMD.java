package net.prison.foggies.core.player.commands.prestige;

import me.lucko.helper.Commands;
import net.milkbowl.vault.economy.Economy;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.storage.PlayerStorage;
import org.bukkit.entity.Player;

public class PrestigeMaxCMD {

    public PrestigeMaxCMD(OPPrison plugin) {
        final PlayerStorage playerStorage = plugin.getPlayerStorage();
        final Economy economy = plugin.getEconomy();

        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    final Player player = c.sender();
                    playerStorage.get(player.getUniqueId())
                            .whenComplete(((pPlayer, throwable) -> {

                                if(throwable != null){
                                    throwable.printStackTrace();
                                    return;
                                }

                                pPlayer.ifPresent(p -> p.prestigeMax(economy));

                            }));
                })
                .register("prestigemax");

    }
}
