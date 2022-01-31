package net.prison.foggies.core.mines.obj;

import com.fastasyncworldedit.core.Fawe;
import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class PersonalMine {

    private UUID mineOwner;
    private long blocksMined, mineLevel;
    private double mineExperience;
    private MineBlock mineBlock;
    private MineRegion mineRegion;
    private boolean isPublic;

    public void addExperience(double amount){
        setMineExperience(getMineExperience() + amount);
    }

    public void addLevel(long amount){
        setMineLevel(getMineLevel() + amount);
    }

    public void addBlocksMined(long amount){
        setBlocksMined(getBlocksMined() + amount);
    }

    public void empty(){
        EditSession editSession = Fawe.get().getWorldEdit().newEditSession(FaweAPI.getWorld(mineRegion.getPoint1().getWorld().getName()));
        editSession.setBlocks((Region) mineRegion.toEntireCuboidRegion(), BlockTypes.AIR);
        editSession.flushQueue();
    }

    public void reset(){
        EditSession editSession = Fawe.get().getWorldEdit().newEditSession(FaweAPI.getWorld(mineRegion.getPoint1().getWorld().getName()));
        editSession.setBlocks((Region) mineRegion.toCuboidRegion(), BlockType.REGISTRY.get(mineBlock.getMaterial().toLowerCase()));
        editSession.flushQueue();

        getPlayersInMine().forEach(player -> player.teleport(mineRegion.getSpawnPoint().toBukkitLocation()));
    }

    public List<Player> getPlayersInMine(){
        return Players.stream()
                .filter(player -> mineRegion.outerRegion().contains(player))
                .collect(Collectors.toList());
    }

}
