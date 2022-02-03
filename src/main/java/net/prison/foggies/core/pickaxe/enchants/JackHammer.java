package net.prison.foggies.core.pickaxe.enchants;

import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.pickaxe.api.EnchantBase;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.player.obj.BackPack;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.utils.FaweUtils;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JackHammer extends EnchantBase {

    @Override
    public String getColor() {
        return "&3";
    }

    @Override
    public String getSymbol() {
        return Lang.BLOCK_SYMBOL.getMessage();
    }

    @Override
    public String getDisplayName() {
        return getColor() + "&l" + getSymbol() + getColor() + "JackHammer";
    }

    @Override
    public String getMenuDisplayName() {
        return getColor() + "JackHammer";
    }

    @Override
    public String getIdentifier() {
        return "JACK_HAMMER";
    }

    @Override
    public long getStartLevel() {
        return 10;
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(
                Arrays.asList(
                        "&7Chance to remove an entire layer of the mine."
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
        return 1000000;
    }

    @Override
    public double getCost(long amount) {
        return getBasePrice() * amount;
    }

    /*
        Doesn't affect Player blocks mined as we intend for those blocks
        to be raw.
     */
    @Override
    public void handle(PrisonPlayer prisonPlayer, PlayerPickaxe playerPickaxe, PersonalMine personalMine, BlockBreakEvent e) {
        BackPack backPack = prisonPlayer.getBackPack();
        long blockAffected = FaweUtils.getJackHammer(personalMine, e.getBlock().getLocation());
        personalMine.addBlocksMined(blockAffected);
        playerPickaxe.addBlocksMined(blockAffected);
        backPack.addBlock(personalMine.getMineBlock(), blockAffected);
    }
}
