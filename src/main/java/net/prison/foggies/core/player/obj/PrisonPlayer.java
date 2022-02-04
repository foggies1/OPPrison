package net.prison.foggies.core.player.obj;

import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.Schedulers;
import me.lucko.helper.utils.Players;
import net.milkbowl.vault.economy.Economy;
import net.prison.foggies.core.player.constant.SettingType;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import net.prison.foggies.core.utils.SerializeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Getter
@Setter

public class PrisonPlayer {

    private static final double BASE_COST = 2D;

    private UUID UUID;
    private long prestige;
    private boolean autoPrestige;
    private long tokens;
    private long totalTokensSpent;
    private long totalTokensGained;
    private long blocksMined;
    private BackPack backPack;
    private ArrayList<Setting> settings;

    /**
     * Used for loading a player from database.
     *
     * @param uuid      the UUID of the target player.
     * @param resultSet the set of results gotten from database query.
     * @throws SQLException exception.
     */
    public PrisonPlayer(UUID uuid, ResultSet resultSet) throws SQLException, IOException, ClassNotFoundException {
        this.UUID = uuid;
        this.prestige = resultSet.getLong("PRESTIGE");
        this.autoPrestige = resultSet.getBoolean("AUTO_PRESTIGE");
        this.tokens = resultSet.getLong("TOKENS");
        this.totalTokensSpent = resultSet.getLong("TOTAL_TOKENS_SPENT");
        this.totalTokensGained = resultSet.getLong("TOTAL_TOKENS_GAINED");
        this.blocksMined = resultSet.getLong("BLOCKS_MINED");
        this.backPack = (BackPack) SerializeUtils.fromString(resultSet.getString("BACKPACK"));
        this.settings = (ArrayList<Setting>) SerializeUtils.fromString(resultSet.getString("SETTINGS"));
    }

    /**
     * Used for initialising a new player.
     *
     * @param uuid the UUID of the target player.
     */
    public PrisonPlayer(UUID uuid) {
        this.UUID = uuid;
        this.prestige = 0L;
        this.autoPrestige = false;
        this.tokens = 0L;
        this.totalTokensSpent = 0L;
        this.totalTokensGained = 0L;
        this.blocksMined = 0L;
        this.backPack = new BackPack(new HashMap<>(), 0L, 1L, 1000000000L);

        ArrayList<Setting> settingList = new ArrayList<>();
        Arrays.stream(SettingType.values()).forEach(setting -> settingList.add(new Setting(setting, false)));
        this.settings = settingList;
    }

    public boolean toggle(SettingType settingType) {
        if (!hasSetting(settingType)) getSettings().add(new Setting(settingType, true));

        for (Setting setting : getSettings()) {
            if (setting.getSettingType() == settingType) {
                return setting.toggle();
            }
        }
        return false;
    }

    public Optional<Setting> getSetting(SettingType settingType){
        if(!hasSetting(settingType)) getSettings().add(new Setting(settingType, true));
        return getSettings().stream().filter(setting -> setting.getSettingType() == settingType).findFirst();
    }

    public boolean hasSetting(SettingType settingType) {
        return getSettings().stream().anyMatch(setting -> setting.getSettingType() == settingType);
    }

    public void prestige(Player player, Economy economy) {
        long prestige = getPrestige();
        double cost = Math.pow((prestige + 1) * BASE_COST, 2);
        double money = economy.getBalance(player);

        if (money < cost) {
            Players.msg(player, Lang.NOT_ENOUGH_MONEY.getMessage());
            return;
        }

        economy.withdrawPlayer(player, cost);
        addPrestige(1);

        Players.msg(player, Lang.PRESTIGE.getMessage()
                .replace("%prestiges%", Number.pretty(1))
                .replace("%price%", Number.pretty(cost))
        );
    }

    public void prestigeMax(Player player, Economy economy) {
        Schedulers.async().run(() -> {
            long prestige = getPrestige();
            long prestigeGained = 0L;
            double totalCost = 0.0D;
            double balance = economy.getBalance(player);

            while (true) {

                double cost = Math.pow((prestige + prestigeGained) * BASE_COST, 2);

                if (balance < cost) {
                    break;
                }

                prestigeGained++;
                totalCost += cost;
                balance -= cost;

            }

            economy.withdrawPlayer(player, totalCost);
            addPrestige(prestigeGained);
            Players.msg(player, Lang.PRESTIGE.getMessage()
                    .replace("%prestiges%", Number.pretty(prestigeGained))
                    .replace("%price%", Number.pretty(totalCost))
            );
        });

    }

    public void addBlocksMined(long amount) {
        setBlocksMined(getBlocksMined() + amount);
    }

    public void addTokens(long amount, boolean admin) {
        setTokens(getTokens() + amount);
        if (!admin)
            addTotalTokensGained(amount);
    }

    public void addTotalTokensSpent(long amount) {
        setTotalTokensSpent(getTotalTokensSpent() + amount);
    }

    public void addTotalTokensGained(long amount) {
        setTotalTokensGained(getTotalTokensGained() + amount);
    }


    public void addPrestige(long amount) {
        setPrestige(getPrestige() + amount);
    }

    public void takeBlocksMined(long amount) {
        if (getBlocksMined() - amount < 0) amount = getBlocksMined();
        setBlocksMined(getBlocksMined() - amount);
    }

    public void takePrestige(long amount) {
        if (getPrestige() - amount < 0) amount = getPrestige();
        setPrestige(getPrestige() - amount);
    }

    public void takeTokens(long amount, boolean admin) {
        if (getTokens() - amount < 0) amount = getTokens();
        setTokens(getTokens() - amount);
        if (!admin)
            addTotalTokensSpent(amount);
    }

    public void takeTotalTokensSpent(long amount) {
        if (getTotalTokensSpent() - amount < 0) amount = getTotalTokensSpent();
        setTotalTokensSpent(getTotalTokensSpent() - amount);
    }

    public void takeTotalTokensGained(long amount) {
        if (getTotalTokensGained() - amount < 0) amount = getTotalTokensGained();
        setTotalTokensGained(getTotalTokensGained() - amount);
    }

    public Player toBukkit() {
        return Bukkit.getPlayer(this.UUID);
    }

}
