package net.torosamy.safeTrade.commands

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.scheduler.CheckSuccessTask
import net.torosamy.safeTrade.utils.BlackListUtil
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class PlayerCommands {
    @Command(value = "trade send <player>", requiredSender = Player::class)
    @Permission("safetrade.send")
    @CommandDescription("向某名玩家发起交易请求")
    fun sendTrade(sender: CommandSender, @Argument("player") player: Player) {
        val blackSet = BlackListUtil.map[player.name]
        //如果被发起者没有黑名单 或者黑名单不包含 则继续
        if (blackSet != null && blackSet.contains(sender.name)) {
            sender.sendMessage(MessageUtil.text(ConfigUtil.langConfig.sendFailIgnore).replace("{player}", player.name))
            return
        }

        //前置插件会检测玩家是否存在

        //如果发送失败则返回
        if(!TradeManager.addTrade(sender as Player, player)) return
        //向发送者发送信息
        val senderMessage = HoverUtil.replacePapi(ConfigUtil.langConfig.sendSuccessSelf,sender.name,player.name)
        val cancelHover = HoverUtil.replacePapi(ConfigUtil.langConfig.hoverCancel,sender.name,player.name)
        HoverUtil.sendCommandHover(sender,HoverUtil.createCommandHover(senderMessage,"/trade cancel",cancelHover))
        //向接收者发送信息
        val acceptText = HoverUtil.replacePapi(ConfigUtil.langConfig.textAccept,sender.name,player.name)
        val acceptHover = HoverUtil.replacePapi(ConfigUtil.langConfig.hoverAccept,sender.name,player.name)

        val denyText = HoverUtil.replacePapi(ConfigUtil.langConfig.textDeny,sender.name,player.name)
        val denyHover = HoverUtil.replacePapi(ConfigUtil.langConfig.hoverDeny,sender.name,player.name)

        player.sendMessage(MessageUtil.text(ConfigUtil.langConfig.sendSuccessOther))
        HoverUtil.sendCommandHover(player,HoverUtil.createCommandHover(acceptText,"/trade accept",acceptHover))
        HoverUtil.sendCommandHover(player,HoverUtil.createCommandHover(denyText,"/trade deny",denyHover))
    }

    @Command(value = "trade cancel", requiredSender = Player::class)
    @Permission("safetrade.cancel")
    @CommandDescription("取消自己的交易请求")
    fun cancelTrade(sender: CommandSender) {
        if (TradeManager.removeTrade(sender.name)) { MessageUtil.text(ConfigUtil.langConfig.cancelSuccess) }
        else MessageUtil.text(ConfigUtil.langConfig.cancelFail)
    }

    @Command(value = "trade accept", requiredSender = Player::class)
    @Permission("safetrade.accept")
    @CommandDescription("接受与自己相关的交易请求")
    fun acceptTrade(receiver: CommandSender) {
        val index = TradeManager.getTradeIndex(receiver.name)
        if(index == -1) {
            receiver.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.acceptFail,receiver.name))
        }else{
            val trade = TradeManager.tradeList.get(index)

            trade.isHandled = true

            trade.sender.openInventory(trade.tradeInventory.inventory)
            trade.receiver.openInventory(trade.tradeInventory.inventory)
            CheckSuccessTask(ConfigUtil.mainConfig.continueSecond,trade).runTaskTimer(SafeTrade.plugin,0L,20L)

            //向双方发送接受成功的消息
            val senderName = trade.sender.name
            val receiverName = trade.receiver.name
            trade.sender.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.acceptSuccessSender,senderName,receiverName))
            trade.receiver.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.acceptSuccessReceiver,senderName,receiverName))
        }
    }


    @Command(value = "trade deny", requiredSender = Player::class)
    @Permission("safetrade.deny")
    @CommandDescription("拒绝与自己相关的交易请求")
    fun denyTrade(receiver: CommandSender) {
        val index = TradeManager.getTradeIndex(receiver.name)
        if(index == -1) {
            receiver.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.denyFail,receiver.name))
        }else{
            var trade = TradeManager.tradeList.get(index)

            trade.isHandled = true
            TradeManager.removeTrade(index)

            //向双方发送成功拒绝的消息
            val senderName = trade.sender.name
            val receiverName = trade.receiver.name
            trade.sender.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.denySuccessSender,senderName,receiverName))
            trade.receiver.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.denySuccessReceiver,senderName,receiverName))
        }
    }


    @Command(value = "trade ignore <player>", requiredSender = Player::class)
    @Permission("safetrade.ignore")
    @CommandDescription("切换对某个玩家的屏蔽")
    fun ignorePlayer(sender: CommandSender, @Argument("player") player: Player) {
        val blackSet = BlackListUtil.map[sender.name]
        if (blackSet == null) {
            val set = HashSet<String>()
            set.add(player.name)
            BlackListUtil.map[sender.name] = set
            sender.sendMessage(MessageUtil.text(ConfigUtil.langConfig.addIgnore).replace("{player}", player.name))
            return
        }
        //包含则删除
        if(blackSet.contains(player.name)) {
            blackSet.remove(player.name)
            sender.sendMessage(MessageUtil.text(ConfigUtil.langConfig.removeIgnore).replace("{player}", player.name))
            return
        }
        //不包含则添加
        blackSet.add(player.name)
        sender.sendMessage(MessageUtil.text(ConfigUtil.langConfig.addIgnore).replace("{player}", player.name))
    }
}