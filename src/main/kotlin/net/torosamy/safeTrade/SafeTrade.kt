package net.torosamy.safeTrade

import net.torosamy.safeTrade.utils.BlackListUtil
import net.torosamy.safeTrade.utils.CommandUtil
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.ListenerUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SafeTrade : JavaPlugin() {
    companion object{lateinit var plugin: SafeTrade}
    override fun onEnable() {
        plugin = this
        ConfigUtil.reloadConfig()
        CommandUtil.registerCommand()
        ListenerUtil.registerListener()
        BlackListUtil.loadFile()
        BlackListUtil.readFile()

        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&a插件 &eSafeTrade &a成功开启喵~"))
        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&a作者 &eTorosamy|yweiyang"))
    }

    override fun onDisable() {
        ConfigUtil.saveConfig()
        BlackListUtil.writeFile()

        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&c插件 &eSafeTrade &c成功关闭喵~"))
        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&c作者 &eTorosamy|yweiyang"))
    }
}
