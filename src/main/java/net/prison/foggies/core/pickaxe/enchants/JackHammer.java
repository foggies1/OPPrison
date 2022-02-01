package net.prison.foggies.core.pickaxe.enchants;

import com.fastasyncworldedit.core.Fawe;
import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.factory.CuboidRegionFactory;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.pickaxe.api.EnchantBase;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.utils.Cuboid;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.SimpleLocation;
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
        return getColor() +  "&l" + getSymbol() + getColor() + "JackHammer";
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
        return 100;
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
        return 100;
    }

    @Override
    public double getCost(long amount) {
        return getBasePrice() * amount;
    }

    @Override
    public void handle(PrisonPlayer prisonPlayer, PlayerPickaxe playerPickaxe, PersonalMine personalMine, BlockBreakEvent e) {
        CuboidRegion region = personalMine.getMineRegion().toCuboidRegion().clone();
        region.getCenter().setComponents(region.getCenter().getX(), e.getBlock().getY(), region.getCenter().getZ());

        EditSession editSession = Fawe.instance().getWorldEdit().newEditSession(FaweAPI.getWorld("mines"));
        editSession.setBlocks(new CuboidRegionFactory().createCenteredAt(region.getCenter().toBlockPoint(), 10), BlockTypes.AIR);
        editSession.flushQueue();
    }
}
