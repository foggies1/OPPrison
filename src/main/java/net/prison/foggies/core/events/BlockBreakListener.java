package net.prison.foggies.core.events;

import com.mojang.datafixers.util.Either;
import me.lucko.helper.Events;
import net.minecraft.world.level.block.Blocks;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.mines.storage.MineStorage;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.pickaxe.storage.PickaxeStorage;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.NMS;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

public class BlockBreakListener {

    public BlockBreakListener(OPPrison plugin) {
        final PickaxeStorage pickaxeStorage = plugin.getPickaxeStorage();
        final MineStorage mineStorage = plugin.getMineStorage();
        final PlayerStorage playerStorage = plugin.getPlayerStorage();

        Events.subscribe(BlockBreakEvent.class)
                .handler(event -> {
                            Player player = event.getPlayer();
                            Block brokenBlock = event.getBlock();
                            Optional<PersonalMine> currentMine = mineStorage.get(event.getBlock().getLocation());
                            Optional<PrisonPlayer> prisonPlayer = playerStorage.get(player.getUniqueId());
                            Optional<PlayerPickaxe> playerPickaxe = pickaxeStorage.get(player.getUniqueId());

                            if(currentMine.isEmpty() || prisonPlayer.isEmpty() || playerPickaxe.isEmpty()) {
                                event.setCancelled(true);
                                return;
                            }

                            currentMine.get().addBlocksMined(1L);
                            prisonPlayer.get().addBlocksMined(1L);
                            playerPickaxe.ifPresent(pickaxe -> {
                                pickaxe.addRawBlocksMined(1L);
                                pickaxe.getEnchantments()
                                        .keySet()
                                        .forEach(enchant -> enchant.handle(plugin, event));
                            });

                            NMS.setBlockWithUpdate(brokenBlock.getWorld(), brokenBlock.getLocation(), Blocks.a, false);

                        }
                ).bindWith(plugin);
    }
}
