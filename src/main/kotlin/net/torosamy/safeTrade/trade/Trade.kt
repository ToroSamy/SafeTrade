package net.torosamy.safeTrade.trade

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class Trade: BukkitRunnable {
    companion object {
        private val trades = ArrayList<Trade>()
        private val senderCanSlots: IntArray = intArrayOf(10, 19, 28, 37, 11, 20, 29, 38, 12, 21, 30, 39)
        private val receiverCanSlots: IntArray = intArrayOf(14, 23, 32, 41, 15, 24, 33, 42, 16, 25, 34, 43)
        const val yellowSlot = 49
        const val senderConfirmButtonSlot = 48
        const val receiverConfirmButtonSlot = 50
        val grayFrameSlots: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 53, 13, 22, 31, 40, 46, 47, 51, 52)
        val redButtonItem: ItemStack = createButton(Material.RED_STAINED_GLASS_PANE, ConfigUtil.langConfig.redButtonHover)
        val greenButtonItem: ItemStack = createButton(Material.LIME_STAINED_GLASS_PANE, ConfigUtil.langConfig.greenButtonHover)
        val grayFrameItem: ItemStack = createButton(Material.GRAY_STAINED_GLASS_PANE, ConfigUtil.langConfig.grayFrameHover)
        val yellowConfirmItem: ItemStack = createButton(Material.YELLOW_STAINED_GLASS_PANE, ConfigUtil.langConfig.yellowWaitHover)

        private fun createButton(material: Material, display: String): ItemStack {
            val itemStack = ItemStack(material)
            val itemMeta = itemStack.itemMeta
            itemMeta.itemName(MessageUtil.component(display))
            itemStack.setItemMeta(itemMeta)
            return itemStack
        }

        public fun isTrading(username: String): Boolean {
            for (trade in trades) {
                if (trade.isMember(username)) {
                    return true
                }
            }
            return false
        }

        public fun getTrade(username: String): Trade? {
            for (trade in trades) {
                if (trade.isMember(username)) {
                    return trade
                }
            }
            return null
        }

        public fun removeTrade(username: String): Boolean {
            return trades.removeIf {
                it.isMember(username)
            }
        }

        public fun startTrade(sender: Player, receiver: Player): Trade? {
            if (isTrading(sender.name) || isTrading(receiver.name)) {
                return null
            }
            val trade = Trade(sender, receiver)
            trades.add(trade)
            trade.runTaskTimer(SafeTrade.plugin, 0L, 20L)
            return trade
        }
    }
    private val sender: Player
    
    private val receiver: Player
    
    private var remainSecond = ConfigUtil.mainConfig.continueSecond

    private var isSenderConfirm = false
    
    private var isReceiverConfirm = false
    
    private val giveToSenderKit = Bukkit.createInventory(null,27, MessageUtil.component(ConfigUtil.langConfig.kitTitle))
    
    private val giveToReceiverKit = Bukkit.createInventory(null,27, MessageUtil.component(ConfigUtil.langConfig.kitTitle))
    
    private val menu = createBaseMenu()
    
    private fun createBaseMenu(): Inventory {
        val menu = Bukkit.createInventory(TradeMenuHolder.TRADE_INVENTORY_HOLDER,54,MessageUtil.format(ConfigUtil.langConfig.tradeMenuTitle))
        for (grayFrameSlot in grayFrameSlots) {
            menu.setItem(grayFrameSlot, grayFrameItem);
        }
        menu.setItem(senderConfirmButtonSlot, redButtonItem);
        menu.setItem(receiverConfirmButtonSlot, redButtonItem);
        menu.setItem(yellowSlot, yellowConfirmItem)
        return menu
    }
    
    private constructor(sender: Player, receiver: Player) {
        this.sender = sender
        this.receiver = receiver
    }
    
    fun isMember(username: String): Boolean {
        return this.sender.name == username || this.receiver.name == username
    }

    fun canClick(username: String, slot: Int): Boolean {
        if (this.sender.name == username) {
            return senderCanSlots.contains(slot)
        }
        
        if (this.receiver.name == username) {
            return receiverCanSlots.contains(slot)
        }
        
        return false
    }

    private fun receiverUpdateConfirm(slot: Int) {
        if (slot == receiverConfirmButtonSlot && !this.isReceiverConfirm) {
            this.isReceiverConfirm = true
            this.menu.setItem(receiverConfirmButtonSlot, greenButtonItem)
            return
        }

        this.isReceiverConfirm = false
        this.menu.setItem(receiverConfirmButtonSlot, redButtonItem)
    }
    
    private fun senderUpdateConfirm(slot: Int) {
        if (slot == senderConfirmButtonSlot && !this.isSenderConfirm) {
            this.isSenderConfirm = true
            this.menu.setItem(senderConfirmButtonSlot, greenButtonItem)
            return
        }

        this.isSenderConfirm = false
        this.menu.setItem(senderConfirmButtonSlot, redButtonItem)
    }
    

    fun updateConfirm(username: String, slot: Int) {
        if (isSender(username)) {
            senderUpdateConfirm(slot)
            return
        }
    
        receiverUpdateConfirm(slot)
    }

    
    private fun updateYellowButton(remainSecond: Int) {
        val item = this.menu.getItem(yellowSlot) ?: return
        
        val itemMeta = item.itemMeta ?: return
        
        itemMeta.itemName(MessageUtil.component(ConfigUtil.langConfig.yellowConfirmHover.replace("%duration%",remainSecond.toString())))

        item.setItemMeta(itemMeta)
        
        this.menu.setItem(yellowSlot, item)
    }
    fun giveBackItem() {
        for (slot in receiverCanSlots) {
            val item = menu.getItem(slot) ?: continue
            if (item.type == Material.AIR) continue
            this.giveToReceiverKit.addItem(item)
        }

        for (slot in senderCanSlots) {
            val item = menu.getItem(slot) ?: continue
            if (item.type == Material.AIR) continue
            this.giveToSenderKit.addItem(item)
        }
    }

    fun exchangeItem() {
        for (slot in receiverCanSlots) {
            val item = menu.getItem(slot) ?: continue
            if (item.type == Material.AIR) continue
            this.giveToSenderKit.addItem(item)
        }

        for (slot in senderCanSlots) {
            val item = menu.getItem(slot) ?: continue
            if (item.type == Material.AIR) continue
            this.giveToReceiverKit.addItem(item)
        }
    }

    fun openMenu() {
        this.sender.openInventory(this.menu)
        this.receiver.openInventory(this.menu)
    }

    fun openKit() {
        this.sender.openInventory(this.giveToSenderKit)
        this.receiver.openInventory(this.giveToReceiverKit)
    }

    fun isSender(username: String): Boolean {
        return this.sender.name == username
    }
    
    fun isReceiver(username: String): Boolean {
        return this.receiver.name == username
    }
    
    override fun run() {
        if(!this.isSenderConfirm || !this.isReceiverConfirm) {
            menu.setItem(yellowSlot, yellowConfirmItem)
            this.remainSecond = ConfigUtil.mainConfig.continueSecond
            return
        }
        
        if(this.remainSecond > 0) {
            updateYellowButton(this.remainSecond)
            sender.playSound(sender.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
            receiver.playSound(receiver.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
            remainSecond--
            return
        }
        
        trades.remove(this)
        this.cancel()
        
        exchangeItem()
        openKit()
    }
}