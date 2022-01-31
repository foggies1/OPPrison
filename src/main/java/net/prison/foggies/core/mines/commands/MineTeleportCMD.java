package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.storage.MineStorage;
import org.bukkit.entity.Player;

public class MineTeleportCMD {

    public MineTeleportCMD(OPPrison plugin) {
        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPlayer()
                .handler(c -> {
                    final Player player = c.sender();
                    mineStorage.get(player.getUniqueId()).ifPresent(personalMine -> player.teleport(personalMine.getMineRegion().getSpawnPoint().toBukkitLocation()));
                })
                .register("minetp");

    }
}
