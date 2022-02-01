package net.prison.foggies.core.pickaxe.enchants;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.api.EnchantBase;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyFinder extends EnchantBase {

    @Override
    public String getColor() {
        return "&a";
    }

    @Override
    public String getSymbol() {
        return Lang.BLOCK_SYMBOL.getMessage();
    }

    @Override
    public String getDisplayName() {
        return getColor() + "&l" + getSymbol() + getColor() + "KeyFinder";
    }

    @Override
    public String getMenuDisplayName() {
        return getColor() + "&lKeyFinder";
    }

    @Override
    public String getIdentifier() {
        return "KEY_FINDER";
    }

    @Override
    public long getStartLevel() {
        return 10;
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(
                Arrays.asList(
                        "&7Find more keys while mining."
                )
        );
    }

    @Override
    public long getMaxLevel() {
        return 10000;
    }

    @Override
    public long getAdminMaxLevel() {
        return Long.MAX_VALUE;
    }

    @Override
    public float getChance() {
        return 1.0F;
    }

    @Override
    public double getBasePrice() {
        return 0;
    }

    @Override
    public double getCost(long amount) {
        return getBasePrice() * amount;
    }

    @Override
    public void handle(OPPrison plugin, BlockBreakEvent event) {
        final PlayerStorage playerStorage = plugin.getPlayerStorage();
        Player player = event.getPlayer();

        playerStorage.get(player.getUniqueId())
                .whenComplete((prisonPlayer, throwable) -> {

                    if(throwable != null){
                        throwable.printStackTrace();
                        return;
                    }

                    prisonPlayer.ifPresent(pp -> {
                        pp.addExperience(3L);
                        pp.addLevel(10, false);
                    });

                });
    }
}
