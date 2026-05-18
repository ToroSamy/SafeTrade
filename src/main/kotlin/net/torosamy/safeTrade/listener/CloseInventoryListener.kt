package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.trade.Trade
import net.torosamy.safeTrade.trade.TradeMenuHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

class CloseInventoryListener : Listener {
    @EventHandler
    fun closeInventory(event: InventoryCloseEvent) {
        val player = event.player

        if (!TradeMenuHolder.isTradeInventory(event.inventory)) return

        val trade = Trade.getTrade(player.name) ?: return

        Trade.removeTrade(player.name)
        
        trade.giveBackItem()
        trade.openKit()
        
    }
}