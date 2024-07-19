package net.torosamy.safetradeywy.pojo.trade;


import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class Trade {

    private TradeInventory tradeInventory;
    private Player sender;
    private Player receiver;
    private Boolean confirmSender;
    private Boolean confirmReceiver;
    private Boolean confirmRequest;

    private Inventory GiveToSenderKit;
    private Inventory GiveToReceiverKit;

    public Trade(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.confirmSender = false;
        this.confirmReceiver = false;
        this.confirmRequest = false;
        this.tradeInventory = new TradeInventory(this);
        this.GiveToReceiverKit = Bukkit.createInventory(null,27, MessageUtils.text("&4请立即取走物品!"));
        this.GiveToSenderKit = Bukkit.createInventory(null,27,MessageUtils.text("&4请立即取走物品!"));
        new TradeCancelClock(this).runTaskTimerAsynchronously(SafeTradeYwY.getPlugin(),0L,20L);
    }
    public void clearGiveToReceiverKit() {
        this.GiveToReceiverKit.clear();
    }
    public void updateGiveToReceiverKit() {
        clearGiveToReceiverKit();
        for (int slot : TradeInventory.senderCanSlots) {
            ItemStack item = this.tradeInventory.getInventory().getItem(slot);
            if (item == null || item.getType() == Material.AIR) continue;
            this.GiveToReceiverKit.addItem(item);
        }
    }
    public void clearGiveToSenderKit() {
        this.GiveToSenderKit.clear();
    }

    public List<String> getSenderKitNameList() {
        List<String> res = new ArrayList<>();
        for (ItemStack itemStack : GiveToSenderKit) {
            if (itemStack == null || itemStack.getType() == Material.AIR) return res;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasDisplayName()) res.add(itemMeta.getDisplayName()+" - "+itemStack.getAmount());
            else res.add(itemStack.getType().name()+" - "+itemStack.getAmount());
        }
        return res;
    }
    public List<String> getReceiverKitNameList() {
        List<String> res = new ArrayList<>();
        for (ItemStack itemStack : GiveToReceiverKit) {
            if (itemStack == null || itemStack.getType() == Material.AIR) return res;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasDisplayName()) res.add(itemMeta.getDisplayName()+" - "+itemStack.getAmount());
            else res.add(itemStack.getType().name()+" - "+itemStack.getAmount());
        }
        return res;
    }



    public void updateGiveToSenderKit() {
        clearGiveToSenderKit();
        for (int slot : TradeInventory.receiverCanSlots) {
            ItemStack item = this.tradeInventory.getInventory().getItem(slot);
            if (item == null || item.getType() == Material.AIR) continue;
            this.GiveToSenderKit.addItem(item);
        }
    }
    public Inventory getGiveToSenderKit() {
        return GiveToSenderKit;
    }

    public Inventory getGiveToReceiverKit() {
        return GiveToReceiverKit;
    }
    public Boolean getConfirmRequest() {
        return confirmRequest;
    }

    public void setConfirmRequest(Boolean confirmRequest) {
        this.confirmRequest = confirmRequest;
    }
    public Boolean getConfirmSender() {
        return confirmSender;
    }

    public Boolean getConfirmReceiver() {
        return confirmReceiver;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public TradeInventory getTradeInventory() {
        return tradeInventory;
    }

    public void updateConfirm(Integer slot) {
        if (slot == 48) confirmSender = !confirmSender;
        else confirmReceiver = !confirmReceiver;
    }
    public void setConfirmSender(Boolean confirmSender) {
        this.confirmSender = confirmSender;
    }

    public void setConfirmReceiver(Boolean confirmReceiver) {
        this.confirmReceiver = confirmReceiver;
    }

}
