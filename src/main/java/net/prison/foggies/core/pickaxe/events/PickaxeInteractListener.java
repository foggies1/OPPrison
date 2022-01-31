package net.prison.foggies.core.pickaxe.events;

import me.lucko.helper.Events;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.PickaxeHandler;
import net.prison.foggies.core.pickaxe.ui.EnchantUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PickaxeInteractListener {

    public PickaxeInteractListener(final OPPrison plugin) {
        final PickaxeHandler pickaxeHandler = plugin.getPickaxeHandler();
        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getHand() == EquipmentSlot.HAND)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_AIR)
                .filter(event -> event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE)
                .handler(e -> {
                    Player player = e.getPlayer();
                    new EnchantUI(plugin, player, player.getInventory().getItemInMainHand()).open();
                });
    }
}
