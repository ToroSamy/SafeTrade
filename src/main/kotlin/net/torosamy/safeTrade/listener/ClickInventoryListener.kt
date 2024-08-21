package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.pojo.Trade
import net.torosamy.safeTrade.pojo.TradeInventory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

class ClickInventoryListener : Listener {
    @EventHandler
    fun inventoryClickInventoryEvent(event: InventoryClickEvent) {
        //如果点击容器的人不是玩家
        if (event.whoClicked !is Player) return

        val player: Player = event.whoClicked as Player

        //查找对应的交易
        val index = TradeManager.getTradeIndex(player.name)
        if (index == -1) return
        val trade: Trade = TradeManager.tradeList.get(index)
        if (event.inventory != trade.tradeInventory.inventory) return
        //如果已经同意或拒绝了交易 并且交易还未完成则监听

        if (!trade.isHandled || trade.tradeInventory.isFinished) { return }

        //如果对应交易的发送者与点击容器的玩家名字相符
        //那么 取消发送者不可点击的位置
        //否则 取消接受者不可点击的位置
        if (trade.sender.name == player.name) {
            cancelClick(event,trade,48,TradeInventory.senderCannotSlots)
        }else {
            cancelClick(event,trade,50,TradeInventory.receiverCannotSlots)
        }
    }

    @EventHandler
    fun inventoryDragEvent(event: InventoryDragEvent) {
        //如果点击容器的人不是玩家
        if (event.whoClicked !is Player) return

        val player: Player = event.whoClicked as Player

        //查找对应的交易
        val index = TradeManager.getTradeIndex(player.name)
        if (index == -1) return
        val trade: Trade = TradeManager.tradeList.get(index)

        //如果点击的容器不是交易相关的容器 则取消
        if (event.inventory != trade.tradeInventory.inventory) return

        //如果对应交易的发送者与点击容器的玩家名字相符
        val isSender:Boolean = trade.sender.name == player.name
        //那么 取消发送者不可点击的位置
        //否则 取消接受者不可点击的位置
        val cannotSlots:IntArray
        if(isSender) cannotSlots =TradeInventory.senderCannotSlots
        else cannotSlots = TradeInventory.receiverCannotSlots
        for (rawSlot in event.rawSlots) {
            for (cannotSlot in cannotSlots) {
                if(cannotSlot == rawSlot) {
                    event.isCancelled = true
                    return
                }
            }
        }
        if(isSender) trade.tradeInventory.falseConfirmStatus(48)
        else trade.tradeInventory.falseConfirmStatus(50)
    }


    companion object {
        /**
         * @param event 对应的时间
         * @param trade 对应的交易
         * @param slot 完成准备按钮的位置
         * @param canSlots 可以点击的位置
         * @param cannotSlots 不可以点击的位置
         */
        fun cancelClick(event: InventoryClickEvent, trade: Trade, slot: Int, cannotSlots: IntArray) {
            if (event.rawSlot == slot) {
                trade.tradeInventory.updateConfirmStatus(slot)
                event.isCancelled = true
                return
            }

            for(cannot in cannotSlots) {
                if(event.rawSlot == cannot) {
                    event.isCancelled = true
                    return
                }
            }


            if(event.isShiftClick) {
                event.isCancelled = true
                return
            }

            if(event.click == ClickType.DOUBLE_CLICK) {
                event.isCancelled = true
                return
            }

            trade.tradeInventory.falseConfirmStatus(slot)
        }
    }
}