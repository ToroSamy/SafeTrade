package net.torosamy.safeTrade.commands

import net.torosamy.safeTrade.api.TradeAPI
import net.torosamy.safeTrade.trade.TradeRequest
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.torosamyCore.request.RequestManager
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class PlayerCommands {
    @Command(value = "trade send <player>")
    @Permission("safetrade.send")
    @CommandDescription("向玩家发起交易请求")
    fun sendTrade(sender: Player, @Argument("player") receiver: Player) {
        if (sender.name == receiver.name) {
            sender.sendMessage(MessageUtil.component(ConfigUtil.langConfig.selfError))
            return
        }
        
        if (TradeAPI.isIgnored(sender.name, receiver.name) || TradeAPI.isIgnored(receiver.name, sender.name)) {
            sender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.denyByIgnore).replace("%player%", receiver.name))
            return
        }

        RequestManager.getInstance().sendRequest(TradeRequest(sender, receiver))

    }

    @Command(value = "trade cancel")
    @Permission("safetrade.cancel")
    @CommandDescription("取消交易请求")
    fun cancelTrade(sender: Player) {
        val instance = RequestManager.getInstance()
        
        val request: TradeRequest? = instance.getRequestBySenderName(sender.name, TradeRequest::class.java)

        if (request == null) {
            sender.sendMessage(MessageUtil.component(ConfigUtil.langConfig.tradeRequestNotFound))
            return
        }

        instance.cancelRequest(request)
    }

    @Command(value = "trade accept")
    @Permission("safetrade.accept")
    @CommandDescription("接受与自己相关的交易请求")
    fun acceptTrade(receiver: Player) {
        val instance = RequestManager.getInstance()

        val request: TradeRequest? = instance.getRequestByReceiverName(receiver.name, TradeRequest::class.java)

        if (request == null) {
            receiver.sendMessage(MessageUtil.format(ConfigUtil.langConfig.tradeRequestNotFound))
            return
        }

        instance.acceptRequest(request)
    }


    @Command(value = "trade deny")
    @Permission("safetrade.deny")
    @CommandDescription("拒绝与自己相关的交易请求")
    fun denyTrade(receiver: Player) {
        val instance = RequestManager.getInstance()

        val request: TradeRequest? = instance.getRequestByReceiverName(receiver.name, TradeRequest::class.java)

        if (request == null) {
            receiver.sendMessage(MessageUtil.component(ConfigUtil.langConfig.tradeRequestNotFound))
            return
        }

        instance.denyRequest(request)
    }


    @Command(value = "trade ignore <player>")
    @Permission("safetrade.ignore")
    @CommandDescription("切换对某个玩家的屏蔽")
    fun ignorePlayer(sender: Player, @Argument("player") player: Player) {
        if (TradeAPI.updateIgnorePlayer(sender.name, player.name)) {
            sender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.addIgnore).replace("%player%", player.name))
            return
        }
        
        sender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.removeIgnore).replace("%player%", player.name))
        return
    }
}