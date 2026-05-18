package net.torosamy.safeTrade.trade

import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import net.torosamy.torosamyCore.request.BaseRequest
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player

class TradeRequest(sender: Player, receiver: Player) :
    BaseRequest(sender, receiver, ConfigUtil.mainConfig.cancelSecond) {

    override fun onTryToSendSame() {
       sender.sendMessage(MessageUtil.component(ConfigUtil.langConfig.tradeRequestRepeat))
    }

    override fun onSend() {
        sender.sendMessage(MessageUtil.component(receiver, HoverUtil.replaceExpired(ConfigUtil.langConfig.tradeRequestSenderMessage)));
        sender.sendMessage(HoverUtil.getComponent(
                MessageUtil.format(receiver, ConfigUtil.langConfig.tradeRequestSenderCancelMessage),
                MessageUtil.format(receiver, ConfigUtil.langConfig.tradeRequestSenderCancelHover),
                MessageUtil.format(receiver, ConfigUtil.langConfig.tradeRequestSenderCancelCommand)
        ));

        receiver.sendMessage(MessageUtil.format(sender, HoverUtil.replaceExpired(ConfigUtil.langConfig.tradeRequestReceiverMessage)));
        receiver.sendMessage(HoverUtil.getComponent(
                MessageUtil.format(sender, ConfigUtil.langConfig.tradeRequestReceiverAcceptMessage),
                MessageUtil.format(sender, ConfigUtil.langConfig.tradeRequestReceiverAcceptHover),
                MessageUtil.format(sender, ConfigUtil.langConfig.tradeRequestReceiverAcceptCommand)
        ));
        receiver.sendMessage(HoverUtil.getComponent(
                MessageUtil.format(sender, ConfigUtil.langConfig.tradeRequestReceiverDenyMessage),
                MessageUtil.format(sender, ConfigUtil.langConfig.tradeRequestReceiverDenyHover),
                MessageUtil.format(sender, ConfigUtil.langConfig.tradeRequestReceiverDenyCommand)
        ));
    }

    override fun onAccept() {
        val trade = Trade.startTrade(sender, receiver)
        
        if (trade == null) {
            receiver.sendMessage(MessageUtil.component(ConfigUtil.langConfig.isTrading))
            sender.sendMessage(MessageUtil.component(ConfigUtil.langConfig.isTrading))
            return
        }

        trade.openMenu()
    }

    override fun onAutoCancel() {
        if (sender.isOnline) {
            sender.sendMessage(MessageUtil.component(receiver, ConfigUtil.langConfig.tradeRequestSenderExpired));
        }

        if (receiver.isOnline) {
            receiver.sendMessage(MessageUtil.component(sender, ConfigUtil.langConfig.tradeRequestReceiverExpired));
        }
    }

    override fun onManualCancel() {
        if (sender.isOnline) {
            sender.sendMessage(MessageUtil.component(ConfigUtil.langConfig.tradeRequestSenderCancel.replace("%player_name%", receiver.name)));
        }

        if (receiver.isOnline) {
            receiver.sendMessage(MessageUtil.component(ConfigUtil.langConfig.tradeRequestReceiverCancel.replace("%player_name%", sender.name)));
        }
    }

    override fun onDeny() {
        sender.sendMessage(MessageUtil.component(receiver, ConfigUtil.langConfig.denySuccessSender))
        receiver.sendMessage(MessageUtil.component(sender,ConfigUtil.langConfig.denySuccessReceiver))
    }
}