package net.prison.foggies.core.events;

import me.lucko.helper.Events;
import net.minecraft.world.level.block.Blocks;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.MineStorage;
import net.prison.foggies.core.pickaxe.PickaxeHandler;
import net.prison.foggies.core.utils.NMS;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener {

    public BlockBreakListener(OPPrison plugin) {
        final PickaxeHandler pickaxeHandler = plugin.getPickaxeHandler();
        final MineStorage mineStorage = plugin.getMineStorage();

        Events.subscribe(BlockBreakEvent.class)
                .handler(event -> {

                            Block brokenBlock = event.getBlock();

                            mineStorage.get(event.getBlock().getLocation()).whenComplete(((personalMine, throwable) -> {

                                if (throwable != null) {
                                    throwable.printStackTrace();
                                    return;
                                }

                                personalMine.ifPresent(mine -> {
                                    mine.addBlocksMined(1L);
                                });

                            }));

                            NMS.setBlockWithUpdate(brokenBlock.getWorld(), brokenBlock.getLocation(), Blocks.a, false);

                        }
                );
    }
}
