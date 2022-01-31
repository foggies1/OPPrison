package net.prison.foggies.core.events;

import me.lucko.helper.Events;
import me.lucko.helper.cooldown.Cooldown;
import me.lucko.helper.cooldown.CooldownMap;
import me.lucko.helper.event.filter.EventFilters;
import me.lucko.helper.utils.Players;
import net.minecraft.world.level.block.Blocks;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.MineStorage;
import net.prison.foggies.core.pickaxe.EnchantHandler;
import net.prison.foggies.core.pickaxe.PickaxeHandler;
import net.prison.foggies.core.pickaxe.PickaxeStorage;
import net.prison.foggies.core.utils.NMS;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import javax.xml.stream.EventFilter;
import java.util.concurrent.TimeUnit;

public class BlockBreakListener {

    public BlockBreakListener(OPPrison plugin) {
        final PickaxeStorage pickaxeStorage = plugin.getPickaxeStorage();
        final MineStorage mineStorage = plugin.getMineStorage();

        CooldownMap<Player> cooldown = CooldownMap.create(Cooldown.of(10L, TimeUnit.SECONDS));

        Events.subscribe(BlockBreakEvent.class)
                .filter(event -> {
                    if(cooldown.test(event.getPlayer())){
                        return true;
                    }

                    Players.msg(event.getPlayer(), "On cooldown, " + cooldown.remainingTime(event.getPlayer(), TimeUnit.SECONDS));
                    event.setCancelled(true);
                    return false;
                })
                .handler(event -> {
                            Player player = event.getPlayer();
                            player.sendMessage("Time changed");
                            Block brokenBlock = event.getBlock();

                            mineStorage.get(event.getBlock().getLocation()).whenComplete(((personalMine, throwable) -> {

                                if (throwable != null) {
                                    throwable.printStackTrace();
                                    return;
                                }

                                personalMine.ifPresent(mine -> {
                                    mine.addBlocksMined(1L);
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
                );
    }
}
