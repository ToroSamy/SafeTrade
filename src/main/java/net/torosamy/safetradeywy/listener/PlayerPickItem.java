package net.torosamy.safetradeywy.listener;

import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickItem implements Listener {
    @EventHandler
    public void onPickItem(PlayerPickupItemEvent event) {
        Integer index = TradeManager.findOnTrade(event.getPlayer().getName());
        if (index == -1) return;
        event.setCancelled(true);
    }
}
