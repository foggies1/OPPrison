package net.prison.foggies.core.crates.handler;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.obj.Crate;
import net.prison.foggies.core.crates.storage.CrateStorage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrateHandler {

    private final OPPrison plugin;
    private final CrateParser parser;
    private final CrateStorage crateStorage;

    public CrateHandler(OPPrison plugin) {
        this.plugin = plugin;
        this.parser = plugin.getCrateParser();
        this.crateStorage = plugin.getCrateStorage();
    }

    public void openCrate(Player player, Crate crate, int amount){
        for(int i=0;i<amount;i++){
            crate.getReward()
                    .getRewards()
                    .forEach(reward -> {
                        parser.parse(reward).apply(player);
                    });
        }
    }

    public void openCrate(Player player, String type, int amount){
        crateStorage.getCrate(type).ifPresent(crate -> openCrate(player, crate, amount));
    }

    public void openCrate(Player player, ItemStack itemStack, int amount){
        crateStorage.getFromKey(itemStack).ifPresent(crate -> openCrate(player, crate, amount));
    }

}
