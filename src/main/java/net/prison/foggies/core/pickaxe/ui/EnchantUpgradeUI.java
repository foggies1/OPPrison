package net.prison.foggies.core.pickaxe.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.model.EnchantBase;
import net.prison.foggies.core.pickaxe.handler.EnchantHandler;
import net.prison.foggies.core.pickaxe.obj.PlayerPickaxe;
import net.prison.foggies.core.pickaxe.storage.PickaxeStorage;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.*;

public class EnchantUpgradeUI extends Gui {

    private OPPrison plugin;
    private EnchantBase enchant;
    private PlayerStorage playerStorage;
    private PickaxeStorage pickaxeStorage;
    private EnchantHandler enchantHandler;

    public EnchantUpgradeUI(Player player, OPPrison plugin, EnchantBase enchant) {
        super(player, 3, "&bUpgrade Enchant");
        this.plugin = plugin;
        this.enchant = enchant;
        this.playerStorage = plugin.getPlayerStorage();
        this.pickaxeStorage = plugin.getPickaxeStorage();
        this.enchantHandler = plugin.getEnchantHandler();
    }

    private static final MenuScheme OUTLINE = new MenuScheme()
            .mask("111111111")
            .mask("000000000")
            .mask("111111111");

    private static final MenuScheme UPGRADES = new MenuScheme()
            .mask("000000000")
            .mask("111111111")
            .mask("000000000");

    @Override
    public void redraw() {

        UUID uuid = getPlayer().getUniqueId();
        Optional<PrisonPlayer> prisonPlayer = playerStorage.get(uuid);
        Optional<PlayerPickaxe> playerPickaxe = pickaxeStorage.get(uuid);

        MenuPopulator upgradePopulator = new MenuPopulator(this, UPGRADES);
        MenuPopulator outlinePopulator = new MenuPopulator(this, OUTLINE);

        if (prisonPlayer.isEmpty() || playerPickaxe.isEmpty()) {
            getPlayer().closeInventory();
            return;
        }

        final long maxLevel = enchant.getMaxLevel();
        final long currentLevel = playerPickaxe.get().getLevel(enchant.getIdentifier());

        long playerTokens = prisonPlayer.map(PrisonPlayer::getTokens).orElse(0L);
        double enchantCost = enchant.getBasePrice();
        boolean reachedMax = maxLevel == currentLevel;

        int[] upgrades = new int[]{1, 10, 25, 50, 100, 250, 500, 750, 1000};

        for (int amount : upgrades) {

            if(amount > maxLevel) amount = (int) maxLevel;
            if(amount + currentLevel > maxLevel) amount = (int) (maxLevel - currentLevel);

            double finalCost = enchantCost * amount;
            boolean hasRequirements = playerTokens >= finalCost;

            String symbol = hasRequirements ? "&a&l" + Lang.BLOCK_SYMBOL.getMessage() : "&4&l" + Lang.BLOCK_SYMBOL.getMessage();
            Material material = hasRequirements ? Material.BOOK : Material.RED_STAINED_GLASS_PANE;
            String name = hasRequirements ? symbol + "UPGRADE X" + amount : symbol + "NOT ENOUGH TOKENS";
            List<String> lore = new ArrayList<>(
                    Arrays.asList(
                            "&7Click here to upgrade the selected enchantment.",
                            "",
                            symbol + "&fCurrent Tokens: " + Number.pretty(playerTokens),
                            symbol + "&fUpgrade Cost: " + Number.pretty(finalCost)
                    )
            );

            if(reachedMax) {
                name = "&4&l" + Lang.BLOCK_SYMBOL.getMessage() + "MAX LEVEL REACHED";
                lore = new ArrayList<>(List.of("&cMaximum level has been achieved, cannot upgrade anymore."));
                material = Material.RED_STAINED_GLASS_PANE;
            }

            int finalAmount = amount;
            upgradePopulator.accept(
                    ItemStackBuilder
                            .of(material)
                            .name(name)
                            .lore(lore)
                            .enchant(Enchantment.DIG_SPEED)
                            .hideAttributes()
                            .build(() -> {

                                if(!hasRequirements){
                                    Players.msg(getPlayer(), Lang.NOT_ENOUGH_TOKENS.getMessage());
                                    return;
                                }

                                if(reachedMax){
                                    Players.msg(getPlayer(), Lang.MAX_ENCHANT_LEVEL_REACHED.getMessage());
                                    return;
                                }

                                playerPickaxe.get().addLevel(enchantHandler, enchant.getIdentifier(), finalAmount);
                                playerPickaxe.get().addTokensSpent((long) finalCost);
                                prisonPlayer.get().takeTokens((long) finalCost, false);
                                redraw();

                                // TODO: Notify that tokens have been taken.
                            })
            );

        }

        outlinePopulator.getSlots().forEach(slot -> ItemStackBuilder.of(Material.GRAY_STAINED_GLASS_PANE).buildItem().build());


    }
}
