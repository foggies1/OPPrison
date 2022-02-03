package net.prison.foggies.core.player.commands;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.commands.prestige.*;
import net.prison.foggies.core.player.commands.tokens.TokenAddCMD;
import net.prison.foggies.core.player.commands.tokens.TokenSetCMD;
import net.prison.foggies.core.player.commands.tokens.TokenTakeCMD;

public class PlayerCommandHandler {

    public PlayerCommandHandler(OPPrison plugin) {
        new AddPrestigeCMD(plugin);
        new PrestigeCMD(plugin);
        new SetPrestigeCMD(plugin);
        new TakePrestigeCMD(plugin);
        new PrestigeMaxCMD(plugin);

        new PlayerDataCMD(plugin);
        new TokenAddCMD(plugin);
        new TokenSetCMD(plugin);
        new TokenTakeCMD(plugin);

        new BackPackCMD(plugin);
    }
}
