package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.pojo.Trade
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import kotlin.math.tan

class CloseInventoryListener : Listener {
    @EventHandler
    fun closeInventory(event: InventoryCloseEvent) {
        var player = event.player

        //查找对应的交易
        val index = TradeManager.getTradeIndex(player.name)
        if (index == -1) return
        val trade: Trade = TradeManager.tradeList.get(index)
        //如果能查找到 并且还已经处理了 那么一定是同意类型的处理 可以认为正在交易当中
        if (!trade.isHandled) return
        //如果点击的容器不是交易相关的容器 则取消
        if (event.inventory != trade.tradeInventory.inventory) return

        trade.updateGiveToReceiverKit()
        trade.updateGiveToSenderKit()

        trade.tradeInventory.isFinished = true
        trade.sender.openInventory(trade.giveToReceiverKit)
        trade.sender.openInventory(trade.giveToSenderKit)

        TradeManager.removeTrade(index)
    }
}