package net.prison.foggies.core.events;

import me.lucko.helper.Events;
import net.minecraft.world.level.block.Blocks;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.storage.MineStorage;
import net.prison.foggies.core.pickaxe.storage.PickaxeStorage;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.NMS;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener {

    public BlockBreakListener(OPPrison plugin) {
        final PickaxeStorage pickaxeStorage = plugin.getPickaxeStorage();
        final MineStorage mineStorage = plugin.getMineStorage();
        final PlayerStorage playerStorage = plugin.getPlayerStorage();

        Events.subscribe(BlockBreakEvent.class)
                .handler(event -> {
                            Player player = event.getPlayer();
                            Block brokenBlock = event.getBlock();

                            mineStorage.get(event.getBlock().getLocation()).whenComplete(((personalMine, throwable) -> {

                                if (throwable != null) {
                                    throwable.printStackTrace();
                                    return;
                                }

                                personalMine.ifPresent(mine -> {
                                    mine.addBlocksMined(1L);

                                    /*
                                        Player Handling ->
                                        Gets the player and then adds blocks mined to the player.
                                     */

                                    playerStorage.get(player.getUniqueId()).join().ifPresent(pp -> pp.addBlocksMined(1));

                                    /*
                                        Pickaxe Handling ->
                                        Gets the pickaxe of the player and adds
                                        blocks mined and then runs through all the enchant
                                        functionality.
                                     */

                                    pickaxeStorage.getFuture(player.getUniqueId())
                                            .whenComplete(((playerPickaxe, throwable1) -> {

                                                if (throwable1 != null) {
                                                    throwable1.printStackTrace();
                                                    return;
                                                }

                                                playerPickaxe.ifPresent(pick -> {
                                                    pick.addRawBlocksMined(1L);
                                                    pick.getEnchantments()
                                                            .keySet()
                                                            .forEach(enchant -> enchant.handle(plugin, event));
                                                });

                                            }));


                                });

                            }));
                            NMS.setBlockWithUpdate(brokenBlock.getWorld(), brokenBlock.getLocation(), Blocks.a, false);
                        }
                ).bindWith(plugin);
    }
}
