package net.torosamy.safeTrade.commands

import net.torosamy.safeTrade.api.TradeAPI
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
        TradeAPI.loadFile()
        sender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.reloadMessage))
    }
}