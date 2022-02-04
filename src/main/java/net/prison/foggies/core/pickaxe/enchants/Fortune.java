package net.prison.foggies.core.pickaxe.enchants;

import me.lucko.helper.Schedulers;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.pickaxe.model.EnchantBase;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fortune extends EnchantBase {

    @Override
    public String getColor() {
        return "&f";
    }

    @Override
    public String getSymbol() {
        return Lang.BLOCK_SYMBOL.getMessage();
    }

    @Override
    public String getDisplayName() {
        return getColor() + "&l" + getSymbol() + getColor() + "Fortune";
    }

    @Override
    public String getMenuDisplayName() {
        return getColor() + "Fortune";
    }

    @Override
    public String getIdentifier() {
        return "FORTUNE";
    }

    @Override
    public long getStartLevel() {
        return 10;
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<>(
                Arrays.asList(
                        "&7Each level multiplies block amounts by",
                        "&7x1 when mining."
                )
        );
    }

    @Override
    public long getMaxLevel() {
        return 5000;
    }

    @Override
    public long getAdminMaxLevel() {
        return Long.MAX_VALUE;
    }

    @Override
    public float getChance() {
        return 0F;
    }

    @Override
    public double getBasePrice() {
        return 10000000D;
    }

    @Override
    public double getCost(long amount) {
        return getBasePrice() * amount;
    }

    @Override
    public void handle(OPPrison plugin, PrisonPlayer prisonPlayer, PlayerPickaxe playerPickaxe, PersonalMine personalMine, BlockBreakEvent e) {
        long level = playerPickaxe.getLevel(getIdentifier());

        Schedulers.async().run(() -> prisonPlayer.getBackPack().addBlock(personalMine.getMineBlock(), level));
    }

}
