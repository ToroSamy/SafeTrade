package net.torosamy.safeTrade.trade

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class TradeMenuHolder : InventoryHolder {
    companion object {
        val TRADE_INVENTORY_HOLDER = TradeMenuHolder()
        
        fun isTradeInventory(inventory: Inventory): Boolean {
            return inventory.holder is TradeMenuHolder
        }
    }
    override fun getInventory(): Inventory {
        throw UnsupportedOperationException("This InventoryHolder is only used as a marker.")
    }
    
}