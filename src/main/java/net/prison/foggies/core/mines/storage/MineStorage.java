package net.prison.foggies.core.mines.storage;

import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.database.MineDatabase;
import net.prison.foggies.core.mines.obj.MineRegion;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.SchematicUtil;
import net.prison.foggies.core.utils.SimpleLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MineStorage {

    private final OPPrison plugin;
    private final MineDatabase database;
    private final File MINE_SCHEMATIC;
    private final MineBlockStorage mineBlockStorage;
    
    private Map<UUID, PersonalMine> mineMap;
    private Location startLocation;

    public MineStorage(final OPPrison plugin) {
        this.plugin = plugin;
        this.mineBlockStorage = plugin.getMineBlockStorage();
        this.database = plugin.getMineDatabase();
        this.MINE_SCHEMATIC = new File(plugin.getSchematicsFolder().toString() + File.separatorChar + "mine.schematic");
        this.startLocation = new Location(plugin.getMineWorld(), 0, 55, 0);
        this.mineMap = loadMines();
    }

    public Map<UUID, PersonalMine> loadMines() {
        return database.loadAllPMines();
    }

    public void unloadMines(){
        mineMap.values().forEach(database::saveMine);
    }

    public void createMine(UUID uuid) {
            if(mineMap.containsKey(uuid)) return;
            updateXLocation(500);

            CompletableFuture.supplyAsync(() -> {
                Location next = getNextLocation().clone();

                SchematicUtil.paste(plugin, MINE_SCHEMATIC, next);

                Location right = next.clone().add(15, 0, 15);
                Location left = next.clone().subtract(15, 39, 15);
                Location maxRight = next.clone().add(37, 0, 37);
                Location maxLeft = next.clone().subtract(37, 39, 37);
                
                PersonalMine personalMine = new PersonalMine(
                        uuid, 0L, 1L,
                        0.0D, mineBlockStorage.getMineBlock(Material.STONE),
                        new MineRegion(new SimpleLocation(next), new SimpleLocation(right),
                                new SimpleLocation(left), new SimpleLocation(maxRight),
                                new SimpleLocation(maxLeft)),
                        new ArrayList<>(),
                        0.01F,
                        false
                );

                mineMap.put(uuid, personalMine);
                database.insertMine(personalMine);
                setLastLocation(next);

                return personalMine;
            }).whenComplete((personalMine, throwable) -> {

                if (throwable != null) {
                    throwable.printStackTrace();
                    return;
                }

                final Player player = Players.getNullable(uuid);
                assert player != null;
                Players.msg(player, Lang.MINE_CREATED.getMessage());

            });

    }

    public Optional<PersonalMine> get(UUID uuid){
        return Optional.ofNullable(mineMap.get(uuid));
    }

    public Optional<PersonalMine> get(Location location){
        return mineMap.values().stream().filter(mine -> mine.getMineRegion().innerRegion().contains(location)).findFirst();
    }

    public boolean isInMine(Location location){
       return mineMap.values().stream().anyMatch(mine -> mine.getMineRegion().innerRegion().contains(location));
    }

    public Location getNextLocation() {
        final Location nextLocation = this.startLocation.clone();
        addDistance(500);
        return nextLocation;
    }

    public void addDistance(int distance) {
        this.startLocation = this.startLocation.add(distance, 0, 0);
    }

    public void setLastLocation(Location next) {
        database.insertLastLocation();
        database.saveLastLocation(next.clone().getBlockX());
    }

    public void updateXLocation(int distance) {
        this.startLocation.setX(database.getLastLocation() + distance);
    }

}
