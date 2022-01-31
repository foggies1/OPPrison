package net.prison.foggies.core.mines;

import lombok.Getter;
import net.prison.foggies.core.OPPrison;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MineBlockStorage {

    private final ConfigurationSection blockSection;
    private Map<String, MineBlock> mineBlocks;

    public MineBlockStorage(OPPrison plugin) {
        this.blockSection = plugin.getConfigManager().getConfig("prices.yml").getConfigurationSection("blocks");
        this.mineBlocks = loadMineBlocks();
    }

    public MineBlock getMineBlock(String name) {
        return mineBlocks.getOrDefault(name, null);
    }

    public MineBlock getMineBlock(Material material) {
        return getMineBlock(material.name());
    }

    public MineBlock getMineBlock(Block block) {
        return getMineBlock(block.getType());
    }

    private Map<String, MineBlock> loadMineBlocks() {
        Map<String, MineBlock> blockMap = new HashMap<>();
        for (String block : blockSection.getKeys(false)) {
            blockMap.put(block.toUpperCase(), new MineBlock(
                    block.toUpperCase(),
                    blockSection.getDouble(block + ".price"),
                    blockSection.getLong(block + ".prestige")
            ));
        }
        return blockMap;
    }

}
