package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.pojo.Trade
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

class PickItemListener : Listener {
    @EventHandler
    fun onPickItem(event: PlayerPickupItemEvent) {
        //查找对应的交易
        val index = TradeManager.getTradeIndex(event.player.name)
        if (index == -1) return
        val trade: Trade = TradeManager.tradeList.get(index)
        //如果能查找到 并且还已经处理了 那么一定是同意类型的处理 可以认为正在交易当中
        if (!trade.isHandled) return
        event.isCancelled = true
    }
}