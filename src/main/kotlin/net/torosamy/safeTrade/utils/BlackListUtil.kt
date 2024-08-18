package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.SafeTrade
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class BlackListUtil {
    companion object {
        //玩家名 对应的黑名单列表
        val map = HashMap<String, HashSet<String>>()
        lateinit var yamlConfiguration: YamlConfiguration
        fun loadFile() {
            val file = File(SafeTrade.plugin.dataFolder.path, "black-list.yml")
            if (!file.exists()) {SafeTrade.plugin.saveResource("black-list.yml", false)}
            yamlConfiguration = YamlConfiguration.loadConfiguration(file)
        }
        fun readFile() {
            for (key in yamlConfiguration.getKeys(false)) {
                map.put(key, yamlConfiguration.get(key) as HashSet<String>)
            }
        }

        fun writeFile() {
            map.forEach { (key, value) -> yamlConfiguration.set(key,HashSet<String>(value)) }
            yamlConfiguration.save(File(SafeTrade.plugin.dataFolder.path, "black-list.yml"))
        }
    }
}