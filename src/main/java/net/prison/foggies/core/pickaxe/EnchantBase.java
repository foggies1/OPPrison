package net.prison.foggies.core.pickaxe;

import net.prison.foggies.core.OPPrison;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public abstract class EnchantBase {

    public abstract String getColor();
    public abstract String getSymbol();
    public abstract String getDisplayName();
    public abstract String getMenuDisplayName();
    public abstract String getIdentifier();
    public abstract long getStartLevel();
    public abstract List<String> getDescription();
    public abstract long getMaxLevel();
    public abstract long getAdminMaxLevel();
    public abstract float getChance();
    public abstract double getBasePrice();
    public abstract void handle(OPPrison plugin, BlockBreakEvent e);

}
