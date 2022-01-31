package net.prison.foggies.core.pickaxe;

import net.prison.foggies.core.OPPrison;
import org.bukkit.event.block.BlockBreakEvent;

public abstract class EnchantBase {

    public abstract String getColor();
    public abstract String getSymbol();
    public abstract String getDisplayName();
    public abstract String getIdentifier();
    public abstract long getStartLevel();
    public abstract long getMaxLevel();
    public abstract long getAdminMaxLevel();
    public abstract float getChance();
    public abstract double getBasePrice();
    public abstract void handle(OPPrison plugin, BlockBreakEvent e);

}
