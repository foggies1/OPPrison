package net.prison.foggies.core.mines.commands;

import me.lucko.helper.Commands;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.storage.MineStorage;
import net.prison.foggies.core.utils.Permissions;
import org.bukkit.OfflinePlayer;

public class FrenzyTokenGiveCMD {

    public FrenzyTokenGiveCMD(OPPrison plugin){
        final MineStorage mineStorage = plugin.getMineStorage();

        Commands.create()
                .assertPermission(Permissions.FRENZY_GIVE.getPermission())
                .assertUsage("<give> <player> <amount")
                .handler(c -> {
                    final OfflinePlayer target = c.arg(1).parseOrFail(OfflinePlayer.class);
                    int amount = c.arg(2).parseOrFail(Integer.class);

                    mineStorage.get(target.getUniqueId()).ifPresent(personalMine -> personalMine.addFrenzyTokens(amount));

                    // TODO: Display give message.
                })
                .register("frenzytoken", "frenzy", "ft");
    }

}
