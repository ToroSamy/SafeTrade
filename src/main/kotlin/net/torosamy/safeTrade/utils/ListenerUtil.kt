package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.listener.*

class ListenerUtil {
    companion object{
        fun registerListener() {
            SafeTrade.plugin.server.pluginManager.registerEvents(InteractEntityListener(), SafeTrade.plugin)
            SafeTrade.plugin.server.pluginManager.registerEvents(ClickInventoryListener(), SafeTrade.plugin)
            SafeTrade.plugin.server.pluginManager.registerEvents(CloseInventoryListener(), SafeTrade.plugin)
            SafeTrade.plugin.server.pluginManager.registerEvents(QuitServerListener(), SafeTrade.plugin)
            SafeTrade.plugin.server.pluginManager.registerEvents(PickItemListener(), SafeTrade.plugin)
        }
    }
}