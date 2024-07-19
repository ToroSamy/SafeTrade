package net.torosamy.safetradeywy.listener;



import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import net.torosamy.safetradeywy.pojo.trade.Trade;
import net.torosamy.safetradeywy.pojo.trade.TradeInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Set;


public class PlayerClickInventory implements Listener {
    private Trade trade;
    private Player player;
    int[] canSlots, cannotSlots;
    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)) return;
        player = (Player) event.getWhoClicked();
        int index = TradeManager.findOnTrade(player.getName());
        if (index == -1) return;
        trade = TradeManager.getTradeList().get(index);
        if (trade.getTradeInventory().getInventory() != event.getInventory()) return;


        if (trade.getSender().getName().equalsIgnoreCase(player.getName())) {
            canSlots = TradeInventory.senderCanSlots;
            cannotSlots = TradeInventory.senderCannotSlots;

            cancelClick(event, 48);
        }else {

            canSlots = TradeInventory.receiverCanSlots;
            cannotSlots = TradeInventory.receiverCannotSlots;

            cancelClick(event, 50);
        }

    }

    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        int index = TradeManager.findOnTrade(player.getName());
        if (index == -1) return;
        Trade trade = TradeManager.getTradeList().get(index);
        if (trade.getTradeInventory().getInventory() != event.getInventory()) return;
        boolean ifSender = trade.getSender().getName().equalsIgnoreCase(player.getName());
        if (ifSender) {
            canSlots = TradeInventory.senderCanSlots;
            cannotSlots = TradeInventory.senderCannotSlots;
        }else {
            canSlots = TradeInventory.receiverCanSlots;
            cannotSlots = TradeInventory.receiverCannotSlots;
        }
        Set<Integer> rawSlots = event.getRawSlots();
        for (Integer rawSlot : rawSlots) {
            for (int cannotSlot : cannotSlots) {
                if (cannotSlot == rawSlot) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if (ifSender) trade.getTradeInventory().falseConfirmItem(48);
        else trade.getTradeInventory().falseConfirmItem(50);
    }


    public void cancelClick(InventoryClickEvent event, Integer slot) {
        //检查是否为确认按钮
        if (event.getRawSlot() == slot) {
            trade.getTradeInventory().updateConfirmItem(slot);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,20,20);
            event.setCancelled(true);
            return;
        }
        //检查是否按住Shift
        if (event.isShiftClick()) {
            for (int canSlot : canSlots) {
                if (event.getRawSlot() == canSlot) {
                    trade.getTradeInventory().falseConfirmItem(slot);
                    return;
                }
            }
            event.setCancelled(true);
            return;
        }
        //禁止双击与单机
        for (int cannotSlot : cannotSlots) {
            if (cannotSlot == event.getRawSlot()) {
                if (event.getClick() == ClickType.DOUBLE_CLICK) {
                    event.setCancelled(true);
                    return;
                }
                event.setCancelled(true);
                return;
            }
        }
        trade.getTradeInventory().falseConfirmItem(slot);
    }
}
