package net.prison.foggies.core.events;

import me.lucko.helper.Events;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.storage.PickaxeStorage;
import net.prison.foggies.core.player.storage.PlayerStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.sql.SQLException;

public class PlayerJoinQuitListener {

    public PlayerJoinQuitListener(OPPrison plugin) {
        final PlayerStorage playerStorage = plugin.getPlayerStorage();
        final PickaxeStorage pickaxeStorage = plugin.getPickaxeStorage();

        Events.subscribe(PlayerJoinEvent.class)
                .handler(event ->  {
                    Player player = event.getPlayer();
                    try {
                        pickaxeStorage.loadPickaxe(player.getUniqueId());
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                    playerStorage.load(player.getUniqueId());
                });

        Events.subscribe(PlayerQuitEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    pickaxeStorage.unloadPickaxe(player.getUniqueId());
                    playerStorage.unload(player.getUniqueId());
                });
    }



}
