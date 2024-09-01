package net.torosamy.safeTrade.commands

import net.torosamy.safeTrade.manager.LogManager
import net.torosamy.safeTrade.utils.BlackListUtil
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class AdminCommands {
    @Command(value = "trade reload")
    @Permission("safetrade.reload")
    @CommandDescription("重载SafeTrade配置文件")
    fun reloadConfig(sender: CommandSender) {
        ConfigUtil.reloadConfig()
        BlackListUtil.readFile()
        sender.sendMessage(MessageUtil.text(ConfigUtil.langConfig.reloadMessage))
    }

    @Command(value = "trade show")
    @Permission("safetrade.show")
    @CommandDescription("输出内存当中的所有logs")
    fun showLogs(sender: CommandSender) {
        LogManager.getLogsInfo().forEach { info: String ->
            sender.sendMessage(info)
        }
    }
}