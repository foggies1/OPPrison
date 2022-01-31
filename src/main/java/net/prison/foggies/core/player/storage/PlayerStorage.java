package net.prison.foggies.core.player.storage;

import lombok.Getter;
import me.lucko.helper.Schedulers;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.player.database.PlayerDatabase;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class PlayerStorage {

    private final Logger log;
    private final OPPrison plugin;
    private final PlayerDatabase playerDatabase;
    private Map<UUID, PrisonPlayer> prisonPlayerMap;

    public PlayerStorage(OPPrison plugin) {
        this.plugin = plugin;
        this.log = plugin.getLogger();
        this.playerDatabase = plugin.getPlayerDatabase();
        this.prisonPlayerMap = new HashMap<>();

        loadAllOnline();
        Schedulers.async()
                .runRepeating(() -> {
                    Players.forEach(player -> save(player.getUniqueId(), false));
                }, 20L, 20L * 60L * 5L);
    }

    public void loadAllOnline(){
        log.log(Level.INFO, "Attempting to load all online players...");
        Players.forEach(player -> {
            load(player.getUniqueId());
        });
        log.log(Level.INFO, "Loading all online players has completed.");
    }

    public void unloadAllOnline(){
        log.log(Level.INFO, "Attempting to unload all online players...");
        Players.forEach(player -> {
            unload(player.getUniqueId());
        });
        log.log(Level.INFO, "Unloading all online players has completed.");
    }

    public void load(UUID uuid){
        final String name = Bukkit.getOfflinePlayer(uuid).getName();

        if(!playerDatabase.contains(uuid)) {
            try {
                PrisonPlayer prisonPlayer = new PrisonPlayer(uuid);

                playerDatabase.insert(prisonPlayer);
                prisonPlayerMap.put(uuid, prisonPlayer);

                log.log(Level.INFO, "Inserted " + name + " into database, and cached.");
            } catch (IOException e){
                log.log(Level.WARNING, "Something when wrong when inserting " + name + " into database.");
            }
            return;
        }

        Optional<PrisonPlayer> prisonPlayer = playerDatabase.get(uuid);
        if(prisonPlayer.isEmpty()) {
            log.log(Level.INFO, "Failed to load " + name + " from database.");
            return;
        }

        prisonPlayerMap.put(uuid, prisonPlayer.get());
        log.log(Level.INFO, "Successfully loaded " + name + " from database and cached.");
    }

    public void unload(UUID uuid){
        final String name = Bukkit.getOfflinePlayer(uuid).getName();
        save(uuid, true);
        log.log(Level.INFO, "Successfully unloaded " + name + ".");
    }

    public void save(UUID uuid, boolean removeFromCache){
        get(uuid).whenComplete(
                (prisonPlayer, throwable) -> {

                    if(throwable != null){
                        throwable.printStackTrace();
                        return;
                    }

                    prisonPlayer.ifPresent(playerDatabase::save);
                    if(removeFromCache)
                        prisonPlayerMap.remove(uuid);

                }
        );
    }

    public CompletableFuture<Optional<PrisonPlayer>> get(UUID uuid){
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(prisonPlayerMap.get(uuid)));
    }

}
