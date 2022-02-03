package net.prison.foggies.core.player.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import net.prison.foggies.core.mines.obj.PersonalMine;
import net.prison.foggies.core.player.obj.PrisonPlayer;
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

        final String prefix = "&4&l" + Lang.BLOCK_SYMBOL.getMessage();

        setItem(20,
                ItemStackBuilder.of(Material.PLAYER_HEAD)
                        .name("&4&lPlayer Data")
                        .lore(
                                "&7Below is &c" + getPlayer().getName() + "'s &7Player data.",
                                "",
                                prefix + "&cPrestige: &f" + Number.pretty(prisonPlayer.getPrestige()),
                                prefix + "&cTokens: &f" + Number.pretty(prisonPlayer.getTokens()),
                                prefix + "&cTotal Tokens Gained: &f" + Number.pretty(prisonPlayer.getTotalTokensGained()),
                                prefix + "&cTotal Tokens Spent: &f" + Number.pretty(prisonPlayer.getTotalTokensSpent()),
                                prefix + "&cBlocks Mined: &f" + Number.pretty(prisonPlayer.getBlocksMined())
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
                        prefix + "&cLevel: &f" + Number.pretty(personalMine.getMineLevel()),
                        prefix + "&cBlocks Mined: &f" + Number.pretty(personalMine.getBlocksMined()),
                        prefix + "&cBlock Value: &f$" + Number.pretty(personalMine.getMineBlock().getSellPrice())
                )
                .enchant(Enchantment.DIG_SPEED)
                .hideAttributes()
                .buildItem().build()
        );
    }
}
