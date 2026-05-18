package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.config.LangConfig
import net.torosamy.safeTrade.config.MainConfig
import net.torosamy.torosamyCore.config.Config
import net.torosamy.torosamyCore.config.ConfigFile

object ConfigUtil {
    private val configs: ArrayList<Config> = ArrayList()

    public var mainConfig: MainConfig = MainConfig()
    public var langConfig: LangConfig = LangConfig()

    fun initConfig() {
        configs.clear()
        configs.add(Config(mainConfig, ConfigFile(SafeTrade.plugin,"config.yml")))
        configs.add(Config(langConfig, ConfigFile(SafeTrade.plugin,"lang.yml")))
    }

    fun reloadConfig() {
        for (config in configs) {
            config.load()
        }
    }

    fun saveConfig() {
        for (config in configs) {
            config.save()
        }
    }
}