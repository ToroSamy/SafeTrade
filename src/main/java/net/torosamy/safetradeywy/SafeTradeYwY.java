package net.torosamy.safetradeywy;

import net.torosamy.safetradeywy.commands.ReloadCommand;
import net.torosamy.safetradeywy.commands.TradeCommand;
import net.torosamy.safetradeywy.listener.PlayerClickInventory;
import net.torosamy.safetradeywy.listener.PlayerCloseInventory;
import net.torosamy.safetradeywy.listener.PlayerOnQuit;
import net.torosamy.safetradeywy.listener.PlayerSendTrade;
import net.torosamy.safetradeywy.utils.DataUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class SafeTradeYwY extends JavaPlugin {
    private static SafeTradeYwY plugin;
    private static Map<String, String> lang = new HashMap<>();
    private static YamlConfiguration logs;




    @Override
    public void onEnable() {
        // Plugin startup logic
        SafeTradeYwY.plugin = this;
        saveDefaultConfig();
        DataUtils.reloadConfig();
        File file = new File(SafeTradeYwY.getPlugin().getDataFolder(), "logs.yml");
        if (!file.exists()) SafeTradeYwY.getPlugin().saveResource("logs.yml",false);
        logs = YamlConfiguration.loadConfiguration(file);

        getServer().getPluginManager().registerEvents(new PlayerSendTrade(),this);
        getServer().getPluginManager().registerEvents(new PlayerOnQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickInventory(), this);
        getServer().getPluginManager().registerEvents(new PlayerCloseInventory(), this);
        Bukkit.getPluginCommand("trade").setExecutor(new TradeCommand());
        Bukkit.getPluginCommand("tradeywy").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SafeTradeYwY getPlugin() {
        return plugin;
    }
    public static Map<String, String> getLang() {
        return lang;
    }
    public static YamlConfiguration getLogs() {
        return logs;
    }
    public static void setLogs(YamlConfiguration logs) {
        SafeTradeYwY.logs = logs;
    }
}
