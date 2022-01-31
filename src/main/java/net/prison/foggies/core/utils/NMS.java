package net.prison.foggies.core.utils;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

public class NMS {

    public static void setBlock(World world, Location location, Block block, boolean applyPhysics) {
        net.minecraft.world.level.World nmsWorld = ((CraftWorld) world).getHandle();
        Chunk nmsChunk = nmsWorld.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
        BlockPosition bp = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        IBlockData ibd = block.getBlockData();
        nmsChunk.setType(bp, ibd, applyPhysics);
    }

    public static void setBlockWithUpdate(World world, Location location, Block block, boolean applyPhysics) {
        net.minecraft.world.level.World nmsWorld = ((CraftWorld) world).getHandle();
        BlockPosition bp = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        IBlockData ibd = block.getBlockData();
        nmsWorld.setTypeAndData(bp, ibd, applyPhysics ? 3 : 2);
    }

}
