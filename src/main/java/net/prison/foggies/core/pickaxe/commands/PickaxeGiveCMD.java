package net.prison.foggies.core.pickaxe.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.PickaxeHandler;
import net.prison.foggies.core.utils.Permissions;
import org.bukkit.entity.Player;

public class PickaxeGiveCMD {

    public PickaxeGiveCMD(OPPrison plugin) {
        final PickaxeHandler pickaxeHandler = plugin.getPickaxeHandler();

        Commands.create()
                .assertPermission(Permissions.PICKAXE_GIVE.getPermission())
                .assertUsage("<player>")
                .handler(c -> {
                    final Player target = c.arg(0).parseOrFail(Player.class);

                    if(target.getInventory().firstEmpty() == -1){
                        // TODO: Inventory full message
                        return;
                    }

                    target.getInventory().addItem(pickaxeHandler.createNewPickaxe());
                    // TODO: Give message and receive message
                })
                .register("pickaxe", "pick");

    }
}
