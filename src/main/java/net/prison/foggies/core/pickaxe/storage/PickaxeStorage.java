package net.prison.foggies.core.pickaxe.storage;

import me.lucko.helper.Schedulers;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.database.PickaxeDatabase;
import net.prison.foggies.core.pickaxe.handler.EnchantHandler;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PickaxeStorage {

    private final OPPrison plugin;
    private final PickaxeDatabase pickaxeDatabase;
    private final EnchantHandler enchantHandler;
    private Map<UUID, PlayerPickaxe> pickaxeMap;

    public PickaxeStorage(OPPrison plugin) {
        this.plugin = plugin;
        this.enchantHandler = plugin.getEnchantHandler();
        this.pickaxeDatabase = plugin.getPickaxeDatabase();
        this.pickaxeMap = new HashMap<>();

        loadAllOnline();
        Schedulers.async().runRepeating(() -> Players.forEach(this::updatePickaxe), 20L, 20L * 10L);
    }

    public void loadAllOnline() {
        Players.forEach(player -> {
            try {
                loadPickaxe(player.getUniqueId());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void unloadAllOnline() {
        Players.forEach(player -> unloadPickaxe(player.getUniqueId()));
    }

    public void loadPickaxe(UUID uuid) throws IOException, SQLException {
        if (pickaxeMap.containsKey(uuid)) return;
        final Player player = Bukkit.getPlayer(uuid);

        pickaxeDatabase.getPickaxe(uuid)
                .whenComplete((pickaxe, throwable) -> {
                    if(throwable != null){
                        throwable.printStackTrace();
                        return;
                    }

                    pickaxe.ifPresentOrElse(pick -> {
                        pickaxeMap.put(uuid, pick);
                        assert player != null;
                        updatePickaxe(player);
                    }, () -> {
                        PlayerPickaxe playerPickaxe = new PlayerPickaxe(enchantHandler, uuid);
                        pickaxeDatabase.insertPickaxe(playerPickaxe);
                        pickaxeMap.put(uuid, playerPickaxe);
                    });

                });
    }

    public void unloadPickaxe(UUID uuid) {
        final Player player = Bukkit.getPlayer(uuid);
        assert player != null;
        updatePickaxe(player);
        save(uuid, true);
    }

    public void updatePickaxe(Player player) {
        get(player.getUniqueId()).ifPresent(pick -> player.getInventory().setItem(0, pick.toItemStack()));
    }

    public Optional<PlayerPickaxe> get(UUID uuid) {
        return Optional.ofNullable(pickaxeMap.get(uuid));
    }

    public void save(UUID uuid, boolean removeFromCache) {

        get(uuid).ifPresent(pick -> {
            try {
                pickaxeDatabase.savePickaxe(pick);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (removeFromCache)
            pickaxeMap.remove(uuid);
    }


}
