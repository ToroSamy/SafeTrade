package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.config.LangConfig
import net.torosamy.safeTrade.config.MainConfig
import net.torosamy.torosamyCore.manager.ConfigManager
class ConfigUtil {
    companion object {
        var mainConfig: MainConfig = MainConfig()
        var langConfig: LangConfig = LangConfig()

        private var mainConfigManager: ConfigManager = ConfigManager(mainConfig, SafeTrade.plugin,"","config.yml")
        private var langConfigManager: ConfigManager = ConfigManager(langConfig, SafeTrade.plugin,"","lang.yml")


        fun reloadConfig() {
            mainConfigManager.load()
            langConfigManager.load()
        }

        fun saveConfig() {
            mainConfigManager.save()
            langConfigManager.save()
        }
    }
}