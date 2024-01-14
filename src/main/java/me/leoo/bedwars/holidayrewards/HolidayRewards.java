package me.leoo.bedwars.holidayrewards;

import com.andrei1058.bedwars.api.BedWars;
import lombok.Getter;
import me.leoo.bedwars.holidayrewards.commands.ReloadCommand;
import me.leoo.bedwars.holidayrewards.configuration.Config;
import me.leoo.bedwars.holidayrewards.listeners.CollectListeners;
import me.leoo.bedwars.holidayrewards.listeners.ItemListeners;
import me.leoo.bedwars.holidayrewards.listeners.ShopListeners;
import me.leoo.bedwars.holidayrewards.utils.RewardsUtil;
import me.leoo.utils.bukkit.Utils;
import me.leoo.utils.bukkit.commands.CommandManager;
import me.leoo.utils.bukkit.config.ConfigManager;
import me.leoo.utils.bukkit.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HolidayRewards extends JavaPlugin {

    private static HolidayRewards instance;

    private ConfigManager mainConfig;
    private BedWars bedWars;

    @Override
    public void onEnable() {
        instance = this;

        Utils.initialize(this);

        if (!Bukkit.getPluginManager().getPlugin("BedWars1058").isEnabled()) {
            bedWars = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
            getLogger().severe("§cBedWars1058 was not found. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

		mainConfig = new Config();

        registerCommands();
        registerEvents();

        RewardsUtil.initializeRewards();

        getLogger().info(getDescription().getName() + " plugin by itz_leoo has been successfully enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(getDescription().getName() + " plugin by itz_leoo has been successfully disabled.");
    }

    private void registerCommands() {
        CommandManager.registerCommands(new ReloadCommand());
    }

    private void registerEvents() {
        Events.register(new CollectListeners(), new ShopListeners(), new ItemListeners());
    }

    public static HolidayRewards get() {
        return instance;
    }
}
