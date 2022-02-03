package net.prison.foggies.core.utils;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.lucko.helper.Schedulers;
import net.prison.foggies.core.mines.obj.PersonalMine;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class FaweUtils {

    /**
     * Simulates the JackHammer enchantment.
     *
     * @param mine     the mine, the enchantment will affect.
     * @param location the location of the broken block.
     * @return the amount of block effected.
     */
    public static long getJackHammer(PersonalMine mine, Location location) {
        final World world = FaweAPI.getWorld(location.getWorld().getName());
        List<Block> blocks = new ArrayList<>();

        Schedulers.async().run(() -> {
            try(EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {

                Location min = mine.getMineRegion().innerRegion().getPoint1();
                Location max = mine.getMineRegion().innerRegion().getPoint2();

                for (int x = (int) max.getX(); x >= (int) min.getX(); x--) {
                    for (int y = location.getBlockY(); y > location.getBlockY() - 1; y--) {
                        for (int z = (int) max.getZ(); z >= (int) min.getZ(); z--) {
                            Block block = location.getWorld().getBlockAt(x, y, z);
                            if (!mine.getMineRegion().innerRegion().contains(block.getLocation())) continue;
                            blocks.add(block);
                            editSession.setBlock(block.getX(), block.getY(), block.getZ(), BlockTypes.AIR);
                        }
                    }
                }

                editSession.flushQueue();

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        return blocks.size();
    }

}
