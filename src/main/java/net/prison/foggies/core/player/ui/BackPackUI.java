package net.prison.foggies.core.player.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.paginated.PaginatedGui;
import me.lucko.helper.menu.paginated.PaginatedGuiBuilder;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.StandardSchemeMappings;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class BackPackUI extends PaginatedGui {

    public BackPackUI(Player player, List<Item> items) {
        super(paginatedGui -> items, player,
                PaginatedGuiBuilder.create()
                        .title("&aGenerator Storage")
                        .nextPageItem(pageInfo ->
                                ItemStackBuilder
                                        .of(Material.STONE_BUTTON)
                                        .name("&aNext Page")
                                        .lore("&7Click here to visit the next page!",
                                                "",
                                                "&7Currently viewing page &b" + pageInfo.getCurrent() + " &7of &b" + pageInfo.getSize()).build()
                        )
                        .previousPageItem(pageInfo ->
                                ItemStackBuilder
                                        .of(Material.STONE_BUTTON)
                                        .name("&cPrevious Page")
                                        .lore("&7Click here to visit the previous page!",
                                                "",
                                                "&7Currently viewing page &b" + pageInfo.getCurrent() + " &7of &b" + pageInfo.getSize()).build()
                        )
                        .scheme((new MenuScheme(StandardSchemeMappings.STAINED_GLASS)).mask("100000001").mask("100000001").mask("100000001").mask("100000001").mask("100000001").mask("100000001")
                                .scheme(15, 15).scheme(15, 15).scheme(15, 15).scheme(15, 15).scheme(15, 15).scheme(15, 15)));

    }
}
