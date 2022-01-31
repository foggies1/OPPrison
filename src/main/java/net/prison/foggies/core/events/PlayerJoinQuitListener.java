package net.prison.foggies.core.events;

import me.lucko.helper.Events;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.PlayerStorage;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener {

    public PlayerJoinQuitListener(OPPrison plugin) {
        final PlayerStorage playerStorage = plugin.getPlayerStorage();

        Events.subscribe(PlayerJoinEvent.class)
                .handler(event -> playerStorage.load(event.getPlayer().getUniqueId()));

        Events.subscribe(PlayerQuitEvent.class)
                .handler(event -> playerStorage.unload(event.getPlayer().getUniqueId()));
    }



}
