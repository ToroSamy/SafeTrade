package net.torosamy.safeTrade.pojo

import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class Trade(var sender: Player,var receiver: Player) {
    var tradeInventory: TradeInventory = TradeInventory(this)
    var isSenderConfirm: Boolean = false
    var isReceiverConfirm: Boolean = false
    var giveToSenderKit: Inventory = Bukkit.createInventory(null,27,MessageUtil.text(ConfigUtil.getLangConfig().giveToSenderTile))
    var giveToReceiverKit: Inventory = Bukkit.createInventory(null,27,MessageUtil.text(ConfigUtil.getLangConfig().giveToReceiverTile))
    var isHandled: Boolean = false
    fun clearGiveToSenderKit() {this.giveToSenderKit.clear()}
    fun updateGiveToSenderKit() {
        clearGiveToSenderKit()
        for (slot in TradeInventory.receiverCanSlots) {
            val item = tradeInventory.inventory.getItem(slot)
            if (item == null || item.type == Material.AIR) continue
            this.giveToSenderKit.addItem(item)
        }
    }
    fun clearGiveToReceiverKit() {this.giveToSenderKit.clear()}
    fun updateGiveToReceiverKit() {
        clearGiveToReceiverKit()
        for (slot in TradeInventory.senderCanSlots) {
            val item = tradeInventory.inventory.getItem(slot)
            if (item == null || item.type == Material.AIR) continue
            this.giveToReceiverKit.addItem(item)
        }
    }

    fun getSenderKitItemInfo():List<String> {
        var res = ArrayList<String>()
        for (itemStack in giveToSenderKit) {
            if (itemStack == null || itemStack.type == Material.AIR) return res
            val itemMeta = itemStack.itemMeta
            if (itemMeta.hasDisplayName()) res.add(itemMeta.displayName + " - " + itemStack.amount)
            else res.add(itemStack.type.name + " - " + itemStack.amount)
        }
        return res
    }

    fun getReceiverKitItemInfo():List<String> {
        var res = ArrayList<String>()
        for (itemStack in giveToReceiverKit) {
            if (itemStack == null || itemStack.type == Material.AIR) return res
            val itemMeta = itemStack.itemMeta
            if (itemMeta.hasDisplayName()) res.add(itemMeta.displayName + " - " + itemStack.amount)
            else res.add(itemStack.type.name + " - " + itemStack.amount)
        }
        return res
    }

    fun updateConfirm(slot : Int) {
        if(slot == 48) isSenderConfirm = !isSenderConfirm
        else isReceiverConfirm = !isReceiverConfirm
    }
}