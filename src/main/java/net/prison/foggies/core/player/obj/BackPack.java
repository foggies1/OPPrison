package net.prison.foggies.core.player.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Item;
import net.prison.foggies.core.mines.obj.MineBlock;
import net.prison.foggies.core.utils.Lang;
import net.prison.foggies.core.utils.Number;
import net.prison.foggies.core.utils.StringUtils;
import org.bukkit.enchantments.Enchantment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class BackPack implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<MineBlock, Long> contents;
    private long totalItemsCollected;
    private double multiplier;
    private long capacity;

    public void insertBlock(MineBlock mineBlock, long amount) {
        if (amount > capacity) amount = capacity - amount;
        contents.put(mineBlock, amount);
    }

    public List<Item> getContents() {
        List<Item> itemStackList = new ArrayList<>();
        for (Map.Entry<MineBlock, Long> blocks : contents.entrySet()) {
            double sellPrice = (blocks.getKey().getSellPrice() * blocks.getValue()) * getMultiplier();

            itemStackList.add(
                    ItemStackBuilder
                            .of(blocks.getKey().toMat())
                            .name("&b" + StringUtils.formatName(blocks.getKey().toMat().name()))
                            .lore(
                                    "&7Click here to sell all of this block type,",
                                    "&7you currently have &bx" + Number.formatted(blocks.getValue()) + " &7stored.",
                                    "",
                                    "&3&l" + Lang.BLOCK_SYMBOL.getMessage() + "&bSell Value: &f$" + Number.pretty(sellPrice)
                            )
                            .enchant(Enchantment.DIG_SPEED)
                            .hideAttributes()
                            .build(() -> {
                                removeBlock(blocks.getKey(), blocks.getValue());
                                // TODO: Make it apply money to the player....
                            })
            );
        }
        return itemStackList;
    }

    public double sellAll() {
        double moneyCollected = 0.0D;
        for (Map.Entry<MineBlock, Long> blocks : contents.entrySet()) {
            moneyCollected += (blocks.getKey().getSellPrice() * blocks.getValue()) + getMultiplier();
            removeBlock(blocks.getKey(), blocks.getValue());
        }
        return moneyCollected;
    }

    public void addBlock(MineBlock mineBlock, long amount) {
        if (!contents.containsKey(mineBlock)) {
            insertBlock(mineBlock, amount);
            return;
        }

        long currentBlocks = contents.get(mineBlock);
        if (currentBlocks + amount > capacity) amount = capacity - currentBlocks;
        contents.replace(mineBlock, currentBlocks + amount);
    }

    public void removeBlock(MineBlock mineBlock, long amount) {
        if (!contents.containsKey(mineBlock)) return;
        long currentAmount = contents.get(mineBlock);
        if (currentAmount - amount < 0) {
            contents.remove(mineBlock);
        } else {
            contents.replace(mineBlock, currentAmount - amount);
        }
    }


}
