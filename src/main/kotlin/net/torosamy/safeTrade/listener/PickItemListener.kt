package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.trade.Trade
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPickupItemEvent

class PickItemListener : Listener {
    @EventHandler
    fun onPickItem(event: PlayerPickupItemEvent) {
        if (Trade.isTrading(event.player.name)) {
            event.isCancelled = true
        }
    }
}