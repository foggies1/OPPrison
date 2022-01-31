package net.prison.foggies.core.pickaxe.enchants;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.EnchantBase;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.event.block.BlockBreakEvent;

public class TokenFinder extends EnchantBase {

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
        return getColor() + "&l" + getSymbol() + "TokenFinder";
    }

    @Override
    public String getIdentifier() {
        return "TOKEN_FINDER";
    }

    @Override
    public long getStartLevel() {
        return 10;
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
    public void handle(OPPrison plugin, BlockBreakEvent event) {

    }
}
