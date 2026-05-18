package net.torosamy.safeTrade.api

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.torosamyCore.config.ConfigFile
import org.bukkit.configuration.file.YamlConfiguration

object TradeAPI {
    private val map = HashMap<String, HashSet<String>>()

    private val configFile = ConfigFile(SafeTrade.plugin, "black-list.yml")

    fun isIgnored(userName: String, targetName: String): Boolean {
        val list = map[userName] ?: return false
        
        return list.contains(targetName)
    }
    
    fun updateIgnorePlayer(userName: String, targetName: String): Boolean {
        val ignoreSet = map.getOrPut(userName) { HashSet() }

        if (ignoreSet.contains(targetName)) {
            ignoreSet.remove(targetName)
            return false
        }

        ignoreSet.add(targetName)
        return true
    }

    fun saveFile() {
        val config = YamlConfiguration()
        map.forEach{
            config.set(it.key, it.value.toList())
        }

        config.save(configFile.getFile(false))
    }

    fun loadFile() {
        map.clear()
        val config = configFile.config

        config.getKeys(false).forEach {
            map[it] = HashSet(config.getStringList(it))
        }
    }
}