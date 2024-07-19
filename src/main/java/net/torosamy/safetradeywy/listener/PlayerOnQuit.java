package net.torosamy.safetradeywy.listener;

import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerOnQuit implements Listener {
    @EventHandler
    public void playerOnQuit(PlayerQuitEvent event) {
        TradeManager.removeAllBySender(event.getPlayer().getName());
        TradeManager.removeAllByReceiver(event.getPlayer().getName());
    }
}
