package net.prison.foggies.core.player.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.player.obj.PrisonPlayer;
import net.prison.foggies.core.utils.Lang;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SettingsUI extends Gui {

    private PrisonPlayer prisonPlayer;
    private final OPPrison plugin;

    public SettingsUI(Player player, Optional<PrisonPlayer> prisonPlayer, OPPrison plugin) {
        super(player, 5, "&bSettings");
        prisonPlayer.ifPresent(value -> this.prisonPlayer = value);
        this.plugin = plugin;
    }

    private static final MenuScheme OUTLINE = new MenuScheme()
            .mask("111111111")
            .mask("000000000")
            .mask("000000000")
            .mask("000000000")
            .mask("111111111");

    private static final MenuScheme SETTINGS = new MenuScheme()
            .mask("000000000")
            .mask("111111111")
            .mask("111111111")
            .mask("111111111")
            .mask("000000000");

    @Override
    public void redraw() {
        final MenuPopulator outlinePopulator = new MenuPopulator(this, OUTLINE);
        final MenuPopulator settingPopulator = new MenuPopulator(this, SETTINGS);
        outlinePopulator.getSlots().forEach(slot -> outlinePopulator.accept(ItemStackBuilder.of(Material.CYAN_STAINED_GLASS_PANE).buildItem().build()));

        prisonPlayer.getSettings()
                .forEach(setting -> {

                    String settingName = setting.getSettingType().getDisplayName();
                    Material settingMat = setting.isToggled() ? Material.CYAN_CONCRETE : Material.RED_CONCRETE;
                    String name = setting.isToggled() ? "&a" + settingName + " &8(&2&lTOGGLED&8)" : "&c" + settingName + " &8(&4&LDISABLED&8)";

                    settingPopulator.accept(
                            ItemStackBuilder
                                    .of(settingMat)
                                    .name(name)
                                    .lore(
                                            setting.getSettingType().getDescription()
                                    )
                                    .enchant(Enchantment.DIG_SPEED).hideAttributes()
                                    .build(() -> {
                                        boolean toggled = prisonPlayer.toggle(setting.getSettingType());
                                        String status = toggled ? "&bEnabled" : "&cDisabled";

                                        Players.msg(getPlayer(),
                                                Lang.TOGGLED_SETTING.getMessage()
                                                        .replace("%status%", status)
                                                        .replace("%setting_type%", settingName)
                                        );

                                        redraw();
                                    })
                    );

                });

    }
}
