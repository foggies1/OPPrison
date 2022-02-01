package net.prison.foggies.core.pickaxe.handler;

import lombok.Getter;
import net.prison.foggies.core.OPPrison;
import net.prison.foggies.core.pickaxe.api.EnchantBase;
import net.prison.foggies.core.pickaxe.enchants.JackHammer;
import net.prison.foggies.core.pickaxe.enchants.KeyFinder;
import net.prison.foggies.core.pickaxe.enchants.MoneyFinder;
import net.prison.foggies.core.pickaxe.enchants.TokenFinder;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class EnchantHandler {

    private final OPPrison plugin;
    private Map<String, EnchantBase> enchantMap;

    public EnchantHandler(OPPrison plugin) {
        this.plugin = plugin;
        this.enchantMap = loadEnchants();
    }

    public Optional<EnchantBase> getEnchant(String name){
        return Optional.ofNullable(enchantMap.get(name.toUpperCase()));
    }

    private Map<String, EnchantBase> loadEnchants(){
        List<EnchantBase> enchants = getEnchants();
        Map<String, EnchantBase> enchantBaseMap = new ConcurrentHashMap<>(enchants.size());
        enchants.forEach(enchant -> enchantBaseMap.put(enchant.getIdentifier(), enchant));
        return enchantBaseMap;
    }


    private List<EnchantBase> getEnchants(){
        return Arrays.asList(
                new TokenFinder(),
                new KeyFinder(),
                new MoneyFinder(),
                new JackHammer()
        );
    }

}
