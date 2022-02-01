package net.prison.foggies.core.player.obj;

import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.text3.Text;
import me.lucko.helper.text3.TextComponent;
import me.lucko.helper.text3.event.HoverEvent;
import net.milkbowl.vault.economy.Economy;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import net.prison.foggies.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Getter
@Setter

public class PrisonPlayer {

    private static final int MAX_LEVEL = 100;
    private static final double PRESTIGE_BASE_COST = 10000.0D;
    private static final double LEVEL_BASE_COST = 1000.0D;

    private UUID UUID;
    private long level;
    private long prestige;
    private double levelExperience;
    private boolean autoPrestige;
    private long tokens;
    private long totalTokensSpent;
    private long totalTokensGained;
    private long blocksMined;

    /**
     * Used for loading a player from database.
     *
     * @param uuid      the UUID of the target player.
     * @param resultSet the set of results gotten from database query.
     * @throws SQLException exception.
     */
    public PrisonPlayer(UUID uuid, ResultSet resultSet) throws SQLException {
        this.UUID = uuid;
        this.level = resultSet.getLong("LEVEL");
        this.prestige = resultSet.getLong("PRESTIGE");
        this.levelExperience = resultSet.getDouble("LEVEL_EXPERIENCE");
        this.autoPrestige = resultSet.getBoolean("AUTO_PRESTIGE");
        this.tokens = resultSet.getLong("TOKENS");
        this.totalTokensSpent = resultSet.getLong("TOTAL_TOKENS_SPENT");
        this.totalTokensGained = resultSet.getLong("TOTAL_TOKENS_GAINED");
        this.blocksMined = resultSet.getLong("BLOCKS_MINED");
    }

    /**
     * Used for initialising a new player.
     *
     * @param uuid the UUID of the target player.
     */
    public PrisonPlayer(UUID uuid) {
        this.UUID = uuid;
        this.level = 1L;
        this.prestige = 0L;
        this.levelExperience = 0.0D;
        this.autoPrestige = false;
        this.tokens = 0L;
        this.totalTokensSpent = 0L;
        this.totalTokensGained = 0L;
        this.blocksMined = 0L;
    }

    public double getLevelUpCost(){
        return Math.pow(getLevel(), 3) * LEVEL_BASE_COST;
    }

    public double getPrestigeCost(long amount){
        return amount * PRESTIGE_BASE_COST;
    }

    public long getMaxPrestigeAmount(double balance){
        return (long) (balance / PRESTIGE_BASE_COST);
    }

    public void prestigeMax(Economy economy){
        long prestiges = getLevel() / MAX_LEVEL;
        double cost = getPrestigeCost(prestiges);

        if(prestiges <= 0) return;
        if(economy.getBalance(toBukkit()) < cost) return;

        economy.withdrawPlayer(toBukkit(), cost);
        addPrestige(prestiges);
        takeLevel(MAX_LEVEL * prestiges);
        sendPrestigeMessage(prestiges, cost);

    }

    public void prestige(Economy economy){
        if(getLevel() < MAX_LEVEL) return;
        double cost = getPrestigeCost(1);
        if(economy.getBalance(toBukkit()) < cost) return;

        economy.withdrawPlayer(toBukkit(), cost);
        addPrestige(1);
        sendPrestigeMessage(1, cost);
    }

    public void addTokens(long amount){
        setTokens(getTokens() + amount);
    }

    public void addTotalTokensSpent(long amount){
        setTotalTokensSpent(getTotalTokensSpent() + amount);
    }

    public void addTotalTokensGained(long amount){
        setTotalTokensGained(getTotalTokensGained() + amount);
    }

    public void addLevel(long amount, boolean viaExperience){
        setLevel(getLevel() + amount);
        if(viaExperience)
            sendLevelUpMessage(amount);
    }

    public void addExperience(double amount){

        double levelUpCost = getLevelUpCost();
        long levels = 0;
        double experience = getLevelExperience() + amount;

        if(experience > levelUpCost){
            while (experience > levelUpCost){
                levels++;
                experience-=levelExperience;
            }
        }

        addLevel(levels, true);
        setLevelExperience(experience);
    }

    public void addPrestige(long amount){
        setPrestige(getPrestige() + amount);
    }

    public void takePrestige(long amount){
        if(getPrestige() - amount < 0) amount = getPrestige();
        setPrestige(getPrestige() - amount);
    }

    public void takeExperience(double amount){
        if(getLevelExperience() - amount < 0) amount = getLevelExperience();
        setLevelExperience(getLevelExperience() - amount);
    }

    public void takeLevel(long amount){
        if(getLevel() - amount < 0) amount = getLevel();
        setLevel(getLevel() - amount);
    }

    public void takeTokens(long amount){
        if(getTokens() - amount < 0) amount = getTokens();
        setTokens(getTokens() - amount);
    }

    public void takeTotalTokensSpent(long amount){
        if(getTotalTokensSpent() - amount < 0) amount = getTotalTokensSpent();
        setTotalTokensSpent(getTotalTokensSpent() - amount);
    }

    public void takeTotalTokensGained(long amount){
        if(getTotalTokensGained() - amount < 0) amount = getTotalTokensGained();
        setTotalTokensGained(getTotalTokensGained() - amount);
    }

    public Player toBukkit() {
        return Bukkit.getPlayer(this.UUID);
    }

    private void sendPrestigeMessage(final long prestigeAmount, final double cost) {
        Text.sendMessage(toBukkit(),
                TextComponent.of(StringUtils.colorPrefix(Lang.PRESTIGE.getMessage()))
                        .hoverEvent(HoverEvent.showText(
                                TextComponent.of(
                                        StringUtils.color("&7Below is a summary of your recent prestige" + "\n" +
                                                "&7activity:" + "\n" +
                                                "" + "\n" +
                                                "&c&l" + Lang.BLOCK_SYMBOL.getMessage() + "&fPrestiges: &4" + Number.pretty(prestigeAmount) + "\n" +
                                                "&c&l" + Lang.BLOCK_SYMBOL.getMessage() + "&fCost: &f$" + Number.pretty(cost)
                                        ))
                        ))
        );
    }

    private void sendLevelUpMessage(final long levelAmount) {
        if(levelAmount <= 0) return;
        Text.sendMessage(toBukkit(),
                TextComponent.of(StringUtils.colorPrefix(Lang.LEVEL_UP.getMessage()))
                        .hoverEvent(HoverEvent.showText(
                                TextComponent.of(
                                        StringUtils.color("&7Below is a summary of your recent levelling" + "\n" +
                                                "&7activity:" + "\n" +
                                                "" + "\n" +
                                                "&c&l" + Lang.BLOCK_SYMBOL.getMessage() + "&fLevels: &4" + Number.pretty(levelAmount)
                                        ))
                        ))
        );
    }

}
