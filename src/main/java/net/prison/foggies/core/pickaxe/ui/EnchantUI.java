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

public class EnchantUI extends Gui {

    private ItemStack itemStack;
    private final EnchantHandler enchantHandler;
    private final PickaxeStorage pickaxeStorage;

    public EnchantUI(OPPrison plugin, Player player, ItemStack itemStack) {
        super(player, 6, "&bEnchantments");
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
                .forEach(enchant ->
                        enchantPopulator.accept(
                                ItemStackBuilder.of(Material.ENCHANTED_BOOK)
                                        .name(enchant.getDisplayName())
                                        .lore(enchant.getDescription())
                                        .enchant(Enchantment.DIG_SPEED)
                                        .hideAttributes()
                                        .build(() -> {
                                            pickaxeStorage.getFuture(getPlayer().getUniqueId())
                                                    .whenComplete((playerPickaxe, throwable) -> {

                                                        if (throwable != null) {
                                                            throwable.printStackTrace();
                                                            return;
                                                        }

                                                        playerPickaxe.ifPresent(pick ->
                                                                pick.addLevel(enchantHandler, enchant.getIdentifier(), 1));

                                                    });
                                            redraw();
                                        })
                        ));

    }
}
