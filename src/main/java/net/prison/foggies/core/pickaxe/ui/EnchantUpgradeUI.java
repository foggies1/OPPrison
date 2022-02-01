package net.prison.foggies.core.pickaxe.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.api.EnchantBase;
import net.prison.foggies.core.pickaxe.handler.EnchantHandler;
import net.prison.foggies.core.pickaxe.storage.PickaxeStorage;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.player.storage.PlayerStorage;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
            .mask("111000111")
            .mask("111111111");

    @Override
    public void redraw() {

        UUID uuid = getPlayer().getUniqueId();

        ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(Material.BOOK)
                .enchant(Enchantment.DIG_SPEED)
                .hideAttributes();

        double baseCost = enchant.getBasePrice();
        Optional<PrisonPlayer> prisonPlayer = playerStorage.get(uuid);
        long tokens = prisonPlayer.map(PrisonPlayer::getTokens).orElse(0L);

        setItem(12,
                itemStackBuilder.name("&cUpgrade x1")
                        .lore(
                                "&7Click here to upgrade x1 of this enchant.",
                                "",
                                "&cCost: &f" + Number.pretty(enchant.getBasePrice()),
                                "&cCurrent Tokens: &f" + Number.pretty(tokens)
                        )
                        .build(() -> {

                            long cost = (long) enchant.getBasePrice();

                            if (tokens < cost) {
                                Players.msg(getPlayer(), Lang.NOT_ENOUGH_TOKENS.getMessage());
                                return;
                            }

                            pickaxeStorage.get(uuid).ifPresent(pickaxe -> {
                                pickaxe.addLevel(enchantHandler, enchant.getIdentifier(), 1);
                            });

                            prisonPlayer.ifPresent(pp -> {
                                pp.takeTokens(cost, false);
                            });

                            redraw();

                        })
        );

        setItem(13,
                itemStackBuilder.name("&cUpgrade x10")
                        .lore(
                                "&7Click here to upgrade x10 of this enchant.",
                                "",
                                "&cCost: &f" + Number.pretty(enchant.getCost(10)),
                                "&cCurrent Tokens: &f" + Number.pretty(tokens)
                        )
                        .build(() -> {

                            long cost = (long) enchant.getCost(10);

                            if (tokens < cost) {
                                Players.msg(getPlayer(), Lang.NOT_ENOUGH_TOKENS.getMessage());
                                return;
                            }

                            pickaxeStorage.get(uuid).ifPresent(pickaxe -> {
                                pickaxe.addLevel(enchantHandler, enchant.getIdentifier(), 10);
                            });

                            prisonPlayer.ifPresent(pp -> {
                                pp.takeTokens(cost, false);
                            });

                            redraw();

                        })
        );

        setItem(14,
                itemStackBuilder.name("&cUpgrade Max")
                        .lore(
                                "&7Click here to upgrade the Max amount you can.",
                                "",
                                "&cCost: &f" + Number.pretty(tokens / baseCost),
                                "&cCurrent Tokens: &f" + Number.pretty(tokens)
                        )
                        .build(() -> {
                            long levels = (long) (tokens / baseCost);

                            pickaxeStorage.get(uuid).ifPresent(pickaxe -> {
                                pickaxe.addLevel(enchantHandler, enchant.getIdentifier(), levels);
                            });

                            prisonPlayer.ifPresent(pp -> {
                                pp.takeTokens(tokens, false);
                            });

                            redraw();

                        })
        );
    }
}
