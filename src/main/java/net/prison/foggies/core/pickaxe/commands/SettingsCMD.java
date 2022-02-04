package net.prison.foggies.core.pickaxe.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.player.ui.SettingsUI;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SettingsCMD {

    public SettingsCMD(OPPrison plugin){
        final PlayerStorage playerStorage = plugin.getPlayerStorage();

        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    final Player player = c.sender();
                    Optional<PrisonPlayer> prisonPlayer = playerStorage.get(player.getUniqueId());
                    new SettingsUI(player, prisonPlayer, plugin).open();
                })
                .register("settings", "options");
    }

}
