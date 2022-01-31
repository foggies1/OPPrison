package net.prison.foggies.core.player.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import net.prison.foggies.core.mines.PersonalMine;
import net.prison.foggies.core.player.PrisonPlayer;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class PlayerDataUI extends Gui {

    private PersonalMine personalMine;
    private PrisonPlayer prisonPlayer;

    public PlayerDataUI(PersonalMine personalMine, PrisonPlayer prisonPlayer, Player player){
        super(player, 5, "&bPlayer Data of " + player.getName());
        this.personalMine = personalMine;
        this.prisonPlayer = prisonPlayer;
    }

    @Override
    public void redraw() {
        if(personalMine == null || prisonPlayer == null) {
            getPlayer().closeInventory();
            return;
        }

        setItem(20,
                ItemStackBuilder.of(Material.PLAYER_HEAD)
                        .name("&4&lPlayer Data")
                        .lore(
                                "&7Below is &c" + getPlayer().getName() + "'s &7Player data.",
                                "",
                                "&4&l" + Lang.BLOCK_SYMBOL.getMessage() + "&cLevel: &f" + Number.pretty(prisonPlayer.getLevel()),
                                "&4&l" + Lang.BLOCK_SYMBOL.getMessage() + "&cPrestige: &f" + Number.pretty(prisonPlayer.getPrestige())
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .buildItem().build()
        );

        setItem(24,
                ItemStackBuilder.of(Material.EMERALD_BLOCK)
                .name("&4&lPersonal Mine Data")
                .lore(
                        "&7Below is &c" + getPlayer().getName() + "'s &7Personal Mine data.",
                        "",
                        "&4&l" + Lang.BLOCK_SYMBOL.getMessage() + "&cLevel: &f" + Number.pretty(personalMine.getMineLevel()),
                        "&4&l" + Lang.BLOCK_SYMBOL.getMessage() + "&cBlocks Mined: &f" + Number.pretty(personalMine.getBlocksMined()),
                        "&4&l" + Lang.BLOCK_SYMBOL.getMessage() + "&cBlock Value: &f$" + Number.pretty(personalMine.getMineBlock().getSellPrice())
                )
                .enchant(Enchantment.DIG_SPEED)
                .hideAttributes()
                .buildItem().build()
        );
    }
}
