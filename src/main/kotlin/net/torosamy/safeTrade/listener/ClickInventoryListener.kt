package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.trade.Trade
import net.torosamy.safeTrade.trade.TradeMenuHolder
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType

class ClickInventoryListener : Listener {
//    private val senderCannotSlots: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25, 26, 27, 31, 32, 33, 34, 35, 36, 40, 41, 42, 43, 44, 45, 46, 47, 49, 50, 51, 52, 53)
//    
//    private val receiverCannotSlots: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 17, 18, 19, 20, 21, 22, 26, 27, 28, 29, 30, 31, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 48, 49, 51, 52, 53)

    @EventHandler
    fun inventoryClickInventoryEvent(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) return

        val player = event.whoClicked as Player

        val trade = Trade.getTrade(player.name) ?: return

        if (!TradeMenuHolder.isTradeInventory(event.inventory)) return

        val inventory = event.clickedInventory ?: return

        trade.updateConfirm(player.name, event.slot)
        
        if (inventory.type == InventoryType.PLAYER) {
            return
        }
        
        if (!trade.canClick(player.name, event.slot)) {
            event.isCancelled = true
            return
        }
    }

    @EventHandler
    fun inventoryDragEvent(event: InventoryDragEvent) {
        if (event.whoClicked !is Player) return
        
        if (!TradeMenuHolder.isTradeInventory(event.inventory)) return

        event.isCancelled = true
    }

//
//    fun cancelClick(event: InventoryClickEvent, trade: Trade, slot: Int, cannotSlots: IntArray) {
//        if (event.rawSlot == slot) {
//            trade.tradeInventory.updateConfirmStatus(slot)
//            event.isCancelled = true
//            return
//        }
//
//        for(cannot in cannotSlots) {
//            if(event.rawSlot == cannot) {
//                event.isCancelled = true
//                return
//            }
//        }
//
//
//        if(event.isShiftClick) {
//            event.isCancelled = true
//            return
//        }
//
//        if(event.click == ClickType.DOUBLE_CLICK) {
//            event.isCancelled = true
//            return
//        }
//
//        trade.tradeInventory.falseConfirmStatus(slot)
//    }
}