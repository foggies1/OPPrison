package net.prison.foggies.core.pickaxe.handler;

import net.prison.foggies.core.OPPrison;

public class PickaxeHandler {

    private final OPPrison plugin;
    private final EnchantHandler enchantHandler;
    private static final String PICKAXE_KEY = "PICKAXE_ITEM";

    public PickaxeHandler(OPPrison plugin) {
        this.plugin = plugin;
        this.enchantHandler = plugin.getEnchantHandler();
    }




}
