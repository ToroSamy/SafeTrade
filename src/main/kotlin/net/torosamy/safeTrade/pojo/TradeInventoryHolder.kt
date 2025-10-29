package net.torosamy.safeTrade.pojo

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class TradeInventoryHolder : InventoryHolder {
    companion object {
        val TRADE_INVENTORY_HOLDER: TradeInventoryHolder = TradeInventoryHolder()
        
        fun isTradeInventory(inventory: Inventory): Boolean {
            return inventory.holder is TradeInventoryHolder
        }
    }
    override fun getInventory(): Inventory {
        throw UnsupportedOperationException("This InventoryHolder is only used as a marker.")
    }
    
}