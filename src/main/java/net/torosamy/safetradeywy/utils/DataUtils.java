package net.torosamy.safetradeywy.utils;

import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.pojo.MainConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DataUtils {
    public static void loadConfig() {
        FileConfiguration config = SafeTradeYwY.getPlugin().getConfig();
        MainConfig.setStartValue(config.getBoolean("start-value"));
        MainConfig.setCancelSecond(config.getInt("cancel-second"));
        MainConfig.setSneakMode(config.getBoolean("sneak-mode"));
        MainConfig.setSuccessSecond(config.getInt("continue-second"));
        MainConfig.setStartLogs(config.getBoolean("start-logs"));

//        MainConfig.setRightClick(config.getBoolean("right-click"));
    }

    public static void loadLang() {
        File file = new File(SafeTradeYwY.getPlugin().getDataFolder(), "lang.yml");
        if (!file.exists()) SafeTradeYwY.getPlugin().saveResource("lang.yml",false);

        YamlConfiguration langFile = YamlConfiguration.loadConfiguration(file);
        langFile.getKeys(true).forEach(message -> {
            String[] split = message.split("\\.");
            SafeTradeYwY.getLang().put(split[split.length-1],langFile.getString(message));
        });

    }


    public static void reloadConfig() {
        loadConfig();
        loadLang();
    }
}
