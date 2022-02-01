package net.prison.foggies.core.pickaxe.api;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class EnchantBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    public abstract double getCost(long amount);
    public abstract void handle(PrisonPlayer prisonPlayer, PlayerPickaxe playerPickaxe, PersonalMine personalMine, BlockBreakEvent e);

}
