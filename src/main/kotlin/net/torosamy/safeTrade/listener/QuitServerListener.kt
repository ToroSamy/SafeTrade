package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.trade.Trade
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class QuitServerListener : Listener {
    @EventHandler
    fun playerOnQuit(event: PlayerQuitEvent) {
        Trade.removeTrade(event.player.name)
    }
}