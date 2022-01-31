package net.prison.foggies.core.mines;

import net.prison.foggies.core.OPPrison;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.Queue;

public class MineQueueHandler {

    private final OPPrison plugin;
    private Queue<PersonalMine> mineResetQueue;
    private BukkitTask resetTask;

    public MineQueueHandler(OPPrison plugin) {
        this.plugin = plugin;
        this.mineResetQueue = new LinkedList<>();
        this.resetTask = beginResetTask();
    }

    public BukkitTask beginResetTask() {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (!mineResetQueue.isEmpty() && mineResetQueue.peek() != null)
                mineResetQueue.poll().reset();
        }, 20L, 20L * 10L);
    }

    public boolean addToQueue(PersonalMine personalMine) {
        if(mineResetQueue.contains(personalMine)) return false;
        mineResetQueue.add(personalMine);
        return true;
    }

    public boolean removeFromQueue(PersonalMine personalMine) {
        if(!mineResetQueue.contains(personalMine)) return false;
        mineResetQueue.remove(personalMine);
        return true;
    }


}
