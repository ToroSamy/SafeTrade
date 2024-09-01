package net.torosamy.safeTrade.pojo

import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class TradeInventory(var trade: Trade) {
    companion object {
        val senderCannotSlots: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25, 26, 27, 31, 32, 33, 34, 35, 36, 40, 41, 42, 43, 44, 45, 46, 47, 49, 50, 51, 52, 53)
        val receiverCannotSlots: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 17, 18, 19, 20, 21, 22, 26, 27, 28, 29, 30, 31, 35, 36, 37, 38, 39, 40, 44, 45, 46, 47, 48, 49, 51, 52, 53)
        val senderCanSlots: IntArray = intArrayOf(10, 19, 28, 37, 11, 20, 29, 38, 12, 21, 30, 39)
        val receiverCanSlots: IntArray = intArrayOf(14, 23, 32, 41, 15, 24, 33, 42, 16, 25, 34, 43)
        val grayFrameSlots: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 53, 13, 22, 31, 40, 46, 47, 51, 52)
        val redButtonSlots: IntArray = intArrayOf(48, 50)
        val confirmButtonSlot: Int = 49
        val redButtonItem: ItemStack = createRedButton()
        val greenButtonItem: ItemStack = createGreenButton()
        val grayFrameItem: ItemStack = createGrayFrame()

        private fun createRedButton():ItemStack {
            var itemStack:ItemStack = ItemStack(Material.RED_STAINED_GLASS_PANE)
            var itemMeta: ItemMeta = itemStack.itemMeta as ItemMeta
            itemMeta.setDisplayName(MessageUtil.text(ConfigUtil.langConfig.redButtonHover))
            itemStack.setItemMeta(itemMeta)
            return itemStack
        }
        private fun createGreenButton():ItemStack {
            var itemStack:ItemStack = ItemStack(Material.LIME_STAINED_GLASS_PANE)
            var itemMeta: ItemMeta = itemStack.itemMeta as ItemMeta
            itemMeta.setDisplayName(MessageUtil.text(ConfigUtil.langConfig.greenButtonHover))
            itemStack.setItemMeta(itemMeta)
            return itemStack
        }

        private fun createGrayFrame(): ItemStack {
            val itemStack = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
            val itemMeta = itemStack.itemMeta
            itemMeta.setDisplayName(MessageUtil.text(ConfigUtil.langConfig.grayFrameItemHover))
            itemStack.setItemMeta(itemMeta)
            return itemStack
        }
    }
    var isFinished: Boolean = false

    var yellowConfirmItem: ItemStack = createYellowConfirmItem()
    fun createYellowConfirmItem(): ItemStack {
        val itemStack = ItemStack(Material.YELLOW_STAINED_GLASS_PANE)
        val itemMeta = itemStack.itemMeta
        itemMeta.setDisplayName(HoverUtil.replacePapi(ConfigUtil.langConfig.yellowNotConfirm,trade.sender.name,trade.receiver.name))
        itemStack.setItemMeta(itemMeta)
        return itemStack
    }

    fun updateYellowButton(duration: Int) {
        val itemMeta = yellowConfirmItem.itemMeta
        //如果duration的时长 < 0 则认为双方未准备完成
        if (duration < 0) itemMeta.setDisplayName(HoverUtil.replacePapi(ConfigUtil.langConfig.yellowNotConfirm,trade.sender.name,trade.receiver.name))
        //如果duration的时长 >= 0 则认为双方正等待交易完成
        else itemMeta.setDisplayName(HoverUtil.replacePapi(ConfigUtil.langConfig.yellowConfirmDuration,trade.sender.name,trade.receiver.name).replace("{duration}",duration.toString()))

        yellowConfirmItem.setItemMeta(itemMeta)
        inventory.setItem(confirmButtonSlot, yellowConfirmItem)
    }


    var inventory: Inventory = createBaseInventory()
    fun createBaseInventory(): Inventory {
        val inventory = Bukkit.createInventory(null,54,MessageUtil.text(ConfigUtil.langConfig.tradeInventoryTitle))
        for (grayFrameSlot in grayFrameSlots) {
            inventory.setItem(grayFrameSlot, grayFrameItem);
        }
        for (redButtonSlot in redButtonSlots) {
            inventory.setItem(redButtonSlot,redButtonItem);
        }
        inventory.setItem(confirmButtonSlot,yellowConfirmItem)
        return inventory
    }

    fun updateConfirmStatus(slot:Int) {
        if(inventory.getItem(slot)?.type  == Material.RED_STAINED_GLASS_PANE)
            inventory.setItem(slot, greenButtonItem)
        else inventory.setItem(slot, redButtonItem)
        trade.updateConfirm(slot)
    }

    fun falseConfirmStatus(slot: Int) {
        if (slot == 48) trade.isSenderConfirm = false
        else trade.isReceiverConfirm = false
        trade.tradeInventory.inventory.setItem(slot, redButtonItem)
    }

}