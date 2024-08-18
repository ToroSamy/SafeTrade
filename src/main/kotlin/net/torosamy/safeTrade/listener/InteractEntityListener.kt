package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.utils.BlackListUtil
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

class InteractEntityListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val sender = event.player
        //没有权限则取消
        if (!sender.hasPermission("safetrade.send")) return
        //检测用户动作是否正确
        if (!(ConfigUtil.getMainConfig().sneakMode && sender.isSneaking)) return

        if (event.hand == EquipmentSlot.HAND) return

        if (event.rightClicked !is Player) return

        val receiver = event.rightClicked as Player


        val blackSet = BlackListUtil.map[receiver.name]
        //如果被发起者没有黑名单 或者黑名单不包含 则继续
        if (blackSet != null && blackSet.contains(sender.name)) {
            sender.sendMessage(MessageUtil.text(ConfigUtil.getLangConfig().sendFailIgnore).replace("{player}", receiver.name))
            return
        }

        //如果玩家不在线是无法被右键到的
        //如果发送失败则返回
        if(!TradeManager.addTrade(sender, receiver)) return

        //向发送者发送信息
        val senderMessage = HoverUtil.replacePapi(ConfigUtil.getLangConfig().sendSuccessSelf,sender.name,receiver.name)
        val cancelHover = HoverUtil.replacePapi(ConfigUtil.getLangConfig().hoverCancel,sender.name,receiver.name)
        HoverUtil.sendCommandHover(sender, HoverUtil.createCommandHover(senderMessage,"/trade cancel",cancelHover))
        //向接收者发送信息
        val acceptText = HoverUtil.replacePapi(ConfigUtil.getLangConfig().textAccept,sender.name,receiver.name)
        val acceptHover = HoverUtil.replacePapi(ConfigUtil.getLangConfig().hoverAccept,sender.name,receiver.name)

        val denyText = HoverUtil.replacePapi(ConfigUtil.getLangConfig().textDeny,sender.name,receiver.name)
        val denyHover = HoverUtil.replacePapi(ConfigUtil.getLangConfig().hoverDeny,sender.name,receiver.name)

        receiver.sendMessage(MessageUtil.text(ConfigUtil.getLangConfig().sendSuccessOther))
        HoverUtil.sendCommandHover(receiver, HoverUtil.createCommandHover(acceptText,"/trade accept",acceptHover))
        HoverUtil.sendCommandHover(receiver, HoverUtil.createCommandHover(denyText,"/trade deny",denyHover))
    }

}