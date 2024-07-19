package net.torosamy.safetradeywy.listener;

import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import net.torosamy.safetradeywy.pojo.trade.Trade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerCloseInventory implements Listener {
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        int index = TradeManager.findOnTrade(player.getName());
        if (index == -1) return;
        Trade trade = TradeManager.getTradeList().get(index);
        if (trade.getTradeInventory().getInventory() != event.getInventory()) return;
        if (trade.getTradeInventory().getFinished()) return;

        trade.updateGiveToSenderKit();
        trade.updateGiveToReceiverKit();
        trade.getTradeInventory().setFinished(true);
        trade.getSender().openInventory(trade.getGiveToReceiverKit());
        trade.getReceiver().openInventory(trade.getGiveToSenderKit());

        TradeManager.removeByIndex(index);
    }
}
