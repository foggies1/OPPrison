package net.prison.foggies.core.crates.handler;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.model.ParsedReward;
import net.prison.foggies.core.crates.rewards.TokenReward;
import net.prison.foggies.core.player.storage.PlayerStorage;

public class CrateParser {

    private OPPrison plugin;
    private PlayerStorage playerStorage;

    public CrateParser(OPPrison plugin) {
        this.plugin = plugin;
        this.playerStorage = plugin.getPlayerStorage();
    }

    public ParsedReward parse(String rewardString){
        String[] rawReward = rewardString.split(" ");

        switch (rawReward[0].toLowerCase()){
            case "token" -> {
                return new TokenReward(plugin, playerStorage, Long.parseLong(rawReward[1]));
            }
        }

        return null;
    }

}
