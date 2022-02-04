package net.prison.foggies.core.crates.storage;

import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.crates.obj.Crate;
import net.prison.foggies.core.utils.PersistentData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrateStorage {

    private final OPPrison plugin;
    private final ConfigurationSection crateSection;
    private Map<String, Crate> crateMap;

    public CrateStorage(OPPrison plugin) {
        this.plugin = plugin;
        this.crateSection = plugin.getConfigManager().getConfig("crates.yml").getConfigurationSection("crates");
        this.crateMap = loadCrates();
    }

    public Optional<Crate> getFromKey(ItemStack itemStack){
        if(itemStack.getType() == Material.AIR) return Optional.empty();
        return getCrate(new PersistentData("crate-key", itemStack).getString());
    }

    public Optional<Crate> getCrate(String identifier){
        return Optional.ofNullable(crateMap.get(identifier));
    }

    private Map<String, Crate> loadCrates(){
        Map<String, Crate> crates = new HashMap<>();
        for(String crate : crateSection.getKeys(false)){
            final ConfigurationSection crateDataSection = crateSection.getConfigurationSection(crate);
            assert crateDataSection != null;
            crates.put(crate, new Crate(crateDataSection, crate));
        }
        return crates;
    }

}
