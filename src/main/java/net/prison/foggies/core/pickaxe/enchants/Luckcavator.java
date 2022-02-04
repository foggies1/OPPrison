package net.prison.foggies.core.pickaxe.enchants;

import me.lucko.helper.Schedulers;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.handler.LuckyBlockHandler;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.pickaxe.model.EnchantBase;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.utils.FaweUtils;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Math;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Luckcavator extends EnchantBase {

    @Override
    public String getColor() {
        return "&6";
    }

    @Override
    public String getSymbol() {
        return Lang.BLOCK_SYMBOL.getMessage();
    }

    @Override
    public String getDisplayName() {
        return getColor() + "&l" + getSymbol() + getColor() + "Luckcavator";
    }

    @Override
    public String getMenuDisplayName() {
        return getColor() + "Luckcavator";
    }

    @Override
    public String getIdentifier() {
        return "LUCKCAVATOR";
    }

    @Override
    public long getStartLevel() {
        return 0;
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(
                Arrays.asList(
                        "&7Chance to remove an entire layer of the mine",
                        "&7but only targets &bLucky Blocks&7."
                )
        );
    }

    @Override
    public long getMaxLevel() {
        return 1;
    }

    @Override
    public long getAdminMaxLevel() {
        return Long.MAX_VALUE;
    }

    @Override
    public float getChance() {
        return 0.001F;
    }

    @Override
    public double getBasePrice() {
        return 100000000000D;
    }

    @Override
    public double getCost(long amount) {
        return getBasePrice() * amount;
    }

    @Override
    public void handle(OPPrison plugin, PrisonPlayer prisonPlayer, PlayerPickaxe playerPickaxe, PersonalMine personalMine, BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final LuckyBlockHandler handler = plugin.getLuckyBlockHandler();
        long level = playerPickaxe.getLevel(getIdentifier());
        if (!Math.isRandom(getChance() * level, getMaxLevel())) return;

        Schedulers.async().run(() -> {
            long blockAffected = FaweUtils.getLuckcavator(personalMine, e.getBlock().getLocation());

            personalMine.addBlocksMined(blockAffected);
            playerPickaxe.addBlocksMined(blockAffected);

            for(int i = 0; i < blockAffected; i++){
                handler.getAndApply(player);
            }

        });
    }
}
