package net.prison.foggies.core.mines.model;

import me.lucko.helper.random.Weighted;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.luckyblock.constant.LuckyType;
import org.bukkit.entity.Player;

public abstract class LuckyBlockReward implements Weighted {

    public abstract void applyReward(OPPrison plugin, Player player);
    public abstract double getWeight();
    public abstract LuckyType getLuckyType();

}
