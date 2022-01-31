package net.prison.foggies.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldUtil {

    private WorldUtil() {
        throw new AssertionError();
    }

    public static World create(String name) {
        World world = Bukkit.getWorld(name);
        if (world != null) {
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(6000L);

            return world;
        }
        world = new WorldCreator(name).environment(World.Environment.NORMAL).generator(new EmptyWorldGenerator()).createWorld();

        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(6000L);
        return world;
    }

}
