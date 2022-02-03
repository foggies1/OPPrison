package net.prison.foggies.core.pickaxe.enchants;

import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.pickaxe.model.EnchantBase;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Producer extends EnchantBase {

    @Override
    public String getColor() {
        return "&e";
    }

    @Override
    public String getSymbol() {
        return Lang.BLOCK_SYMBOL.getMessage();
    }

    @Override
    public String getDisplayName() {
        return getColor() + "&l" + getSymbol() + getColor() + "Producer";
    }

    @Override
    public String getMenuDisplayName() {
        return getColor() + "&lProducer";
    }

    @Override
    public String getIdentifier() {
        return "PRODUCER";
    }

    @Override
    public long getStartLevel() {
        return 5;
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(
                Arrays.asList(
                        "&7Get more tokens each time Token Finder",
                        "&7is activated."
                )
        );
    }

    @Override
    public long getMaxLevel() {
        return 1000000;
    }

    @Override
    public long getAdminMaxLevel() {
        return Long.MAX_VALUE;
    }

    @Override
    public float getChance() {
        return 0.005F;
    }

    @Override
    public double getBasePrice() {
        return 10000000;
    }

    @Override
    public double getCost(long amount) {
        return getBasePrice() * amount;
    }

    @Override
    public void handle(PrisonPlayer prisonPlayer, PlayerPickaxe playerPickaxe, PersonalMine personalMine, BlockBreakEvent e) {

    }
}
