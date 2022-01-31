package net.prison.foggies.core.mines.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.utils.Players;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.mines.MineBlock;
import net.prison.foggies.core.mines.MineQueueHandler;
import net.prison.foggies.core.mines.PersonalMine;
import net.prison.foggies.core.player.PrisonPlayer;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import net.prison.foggies.core.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Map;

public class MineBlockUI extends Gui {

    private final Map<String, MineBlock> mineBlockMap;
    private PrisonPlayer prisonPlayer;
    private PersonalMine personalMine;
    private MineQueueHandler mineQueueHandler;

    public MineBlockUI(OPPrison plugin, Player player) {
        super(player, 5, "&bMine Blocks");
        this.mineBlockMap = plugin.getMineBlockStorage().getMineBlocks();
        this.mineQueueHandler = plugin.getMineQueueHandler();

        plugin.getPlayerStorage().get(player.getUniqueId())
                .whenComplete((prisonPlayer1, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        return;
                    }
                    prisonPlayer1.ifPresent(value -> this.prisonPlayer = value);
                });

        plugin.getMineStorage().get(player.getUniqueId()).ifPresent(mine -> this.personalMine = mine);
    }


    public static final MenuScheme BLOCKS = new MenuScheme()
            .mask("111111111")
            .mask("111111111")
            .mask("111111111")
            .mask("111111111")
            .mask("111111111");

    @Override
    public void redraw() {
        MenuPopulator blocks = new MenuPopulator(this, BLOCKS);

        if (prisonPlayer == null || personalMine == null)
            getPlayer().closeInventory();

        mineBlockMap.values()
                .forEach(block -> {
                            boolean meetsRequirements = prisonPlayer.getPrestige() >= block.getPrestigeRequired();
                            String nameFormatted = StringUtils.formatName(block.getMaterial());
                            final String symbol = "&3&l" + Lang.BLOCK_SYMBOL.getMessage();
                            String displayName = meetsRequirements
                                    ? "&a" + nameFormatted + " &8(&2&lUNLOCKED&8)"
                                    : "&c" + nameFormatted + " &8(&4&lLOCKED&8)";

                            blocks.accept(
                                    ItemStackBuilder.of(
                                                    meetsRequirements ? block.toMat() : Material.BARRIER
                                            )
                                            .name(displayName)
                                            .lore(
                                                    "&7By clicking here it'll change the block within",
                                                    "&7within your &cPersonal Mine&7, this can enable you",
                                                    "&7to make more money.",
                                                    "",
                                                    symbol + "&bCurrent Prestige: &7" + Number.pretty(prisonPlayer.getPrestige()),
                                                    symbol + "&bRequired Prestige: &f" + Number.pretty(block.getPrestigeRequired())
                                            )
                                            .enchant(Enchantment.DIG_SPEED)
                                            .hideAttributes()
                                            .build(() -> {
                                                personalMine.setMineBlock(block);
                                                Players.msg(getPlayer(), Lang.MINE_BLOCK_CHANGED.getMessage().replace("%block%", nameFormatted));

                                                if(mineQueueHandler.addToQueue(personalMine))
                                                    Players.msg(getPlayer(), Lang.MINE_ADDED_TO_QUEUE.getMessage());

                                            })
                            );
                        }
                );
    }
}
