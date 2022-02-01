package net.prison.foggies.core.pickaxe.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.handler.EnchantHandler;
import net.prison.foggies.core.pickaxe.storage.PickaxeStorage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantUI extends Gui {

    private ItemStack itemStack;
    private final EnchantHandler enchantHandler;
    private final PickaxeStorage pickaxeStorage;
    private final OPPrison plugin;

    public EnchantUI(OPPrison plugin, Player player, ItemStack itemStack) {
        super(player, 6, "&bEnchantments");
        this.plugin = plugin;
        this.itemStack = itemStack;
        this.enchantHandler = plugin.getEnchantHandler();
        this.pickaxeStorage = plugin.getPickaxeStorage();
    }

    private static final MenuScheme OUTLINE = new MenuScheme()
            .mask("111111111")
            .mask("111101111")
            .mask("111111111")
            .mask("000000000")
            .mask("000000000")
            .mask("000000000");

    private static final MenuScheme ENCHANTS = new MenuScheme()
            .mask("000000000")
            .mask("000000000")
            .mask("000000000")
            .mask("111111111")
            .mask("111111111")
            .mask("111111111");

    @Override
    public void redraw() {
        MenuPopulator outlinePopulator = new MenuPopulator(this, OUTLINE);
        outlinePopulator.getSlots().forEach(slot -> outlinePopulator.accept(ItemStackBuilder.of(Material.GRAY_STAINED_GLASS_PANE).buildItem().build()));

        setItem(13, ItemStackBuilder.of(itemStack).buildItem().build());

        MenuPopulator enchantPopulator = new MenuPopulator(this, ENCHANTS);

        enchantHandler.getEnchantMap()
                .values()
                .forEach(enchant -> {

                            List<String> lore = enchant.getDescription();
                            lore.add("");
                            lore.add("&7&oClick here to begin Upgrading!");

                            enchantPopulator.accept(
                                    ItemStackBuilder.of(Material.ENCHANTED_BOOK)
                                            .name(enchant.getDisplayName())
                                            .lore(lore)
                                            .enchant(Enchantment.DIG_SPEED)
                                            .hideAttributes()
                                            .build(() -> {
                                                new EnchantUpgradeUI(getPlayer(), plugin, enchant).open();
                                                redraw();
                                            })

                            );

                        }
                );

    }
}

