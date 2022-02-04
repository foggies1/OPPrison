package net.prison.foggies.core.crates.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.handler.CrateHandler;
import net.prison.foggies.core.crates.obj.Crate;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CrateUI extends Gui {

    private Crate crate;
    private OPPrison plugin;
    private CrateHandler crateHandler;

    public CrateUI(OPPrison plugin, Player player, Crate crate) {
        super(player, 6, crate.getDisplayName());
        this.crate = crate;
        this.plugin = plugin;
        this.crateHandler = plugin.getCrateHandler();
    }

    private static final MenuScheme OUTLINE = new MenuScheme()
            .mask("111111111")
            .mask("000000000")
            .mask("000000000")
            .mask("000000000")
            .mask("000000000")
            .mask("111111111");

    private static final MenuScheme REWARDS = new MenuScheme()
            .mask("000000000")
            .mask("111111111")
            .mask("111111111")
            .mask("111111111")
            .mask("111111111")
            .mask("000000000");

    @Override
    public void redraw() {
        MenuPopulator outlinePopulator = new MenuPopulator(this, OUTLINE);
        MenuPopulator rewardPopulator = new MenuPopulator(this, REWARDS);
        outlinePopulator.getSlots().forEach(slot -> outlinePopulator.accept(ItemStackBuilder.of(Material.CYAN_STAINED_GLASS_PANE).buildItem().build()));

        crate.getCrateRewardList()
                .forEach(reward -> rewardPopulator.accept(
                        ItemStackBuilder.of(reward.getItemStack())
                                .build(() -> {
                                    crateHandler.openCrate(getPlayer(), crate, 1);
                                })
                ));

    }
}
