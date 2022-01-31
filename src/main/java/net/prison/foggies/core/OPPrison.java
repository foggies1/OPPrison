package net.prison.foggies.core;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.prison.foggies.core.events.BlockBreakListener;
import net.prison.foggies.core.events.PlayerJoinQuitListener;
import net.prison.foggies.core.mines.MineBlockStorage;
import net.prison.foggies.core.mines.MineDatabase;
import net.prison.foggies.core.mines.MineQueueHandler;
import net.prison.foggies.core.mines.MineStorage;
import net.prison.foggies.core.mines.commands.MineCommandHandler;
import net.prison.foggies.core.pickaxe.EnchantHandler;
import net.prison.foggies.core.pickaxe.PickaxeCommandHandler;
import net.prison.foggies.core.pickaxe.PickaxeHandler;
import net.prison.foggies.core.player.PlayerDatabase;
import net.prison.foggies.core.player.PlayerStorage;
import net.prison.foggies.core.player.commands.PlayerCommandHandler;
import net.prison.foggies.core.utils.ConfigManager;
import net.prison.foggies.core.utils.PersistentData;
import net.prison.foggies.core.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

@Getter
public final class OPPrison extends JavaPlugin {

    private final Logger logger = Bukkit.getLogger();
    private ConfigManager configManager;
    private PlayerStorage playerStorage;
    private PlayerDatabase playerDatabase;
    private MineDatabase mineDatabase;
    private MineStorage mineStorage;
    private MineBlockStorage mineBlockStorage;
    private MineQueueHandler mineQueueHandler;
    private PickaxeHandler pickaxeHandler;
    private EnchantHandler enchantHandler;
    private World mineWorld;
    private File schematicsFolder;

    private Economy economy;


    @Override
    public void onEnable() {
        setupEconomy();

        PersistentData.setPlugin(this);
        this.configManager = ConfigManager.getInstance();
        this.configManager.setPlugin(this);

        this.mineBlockStorage = new MineBlockStorage(this);
        this.mineWorld = WorldUtil.create("mines");
        this.schematicsFolder = createSchematicFolder();
        this.mineDatabase = new MineDatabase("localhost", "3306", "data", "root", "");
        this.mineStorage = new MineStorage(this);
        this.mineQueueHandler = new MineQueueHandler(this);

        this.playerDatabase = new PlayerDatabase("localhost", "3306", "data", "root", "");
        this.playerStorage = new PlayerStorage(this);

        this.enchantHandler = new EnchantHandler(this);
        this.pickaxeHandler = new PickaxeHandler(this);

        new PlayerJoinQuitListener(this);
        new BlockBreakListener(this);

        new PlayerCommandHandler(this);
        new MineCommandHandler(this);
        new PickaxeCommandHandler(this);
    }

    @Override
    public void onDisable() {
        this.playerStorage.unloadAllOnline();
        this.mineStorage.unloadMines();
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        economy = rsp.getProvider();
    }

    private File createSchematicFolder() {
        File file = new File(this.getDataFolder().getAbsolutePath() + "/schematic");
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

}
