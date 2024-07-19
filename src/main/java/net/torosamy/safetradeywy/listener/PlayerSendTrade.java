package net.torosamy.safetradeywy.listener;

import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.pojo.MainConfig;
import net.torosamy.safetradeywy.pojo.trade.Trade;
import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import net.torosamy.safetradeywy.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;


public class PlayerSendTrade implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClickOtherPlayer(PlayerInteractEntityEvent event) {
        Player sender = event.getPlayer();

        //检测用户是否具有权限
        if (!sender.hasPermission("safetradeywy.send")) return;
        //检测用户模式是否正确
        if (!(MainConfig.getSneakMode() && sender.isSneaking())) return;


        if (event.getHand() == EquipmentSlot.HAND) return;
        //检测接受请求的用户类型是否合法
        if (!(event.getRightClicked() instanceof Player)) return;
        Player receiver = (Player) event.getRightClicked();

        //检测用户之间是否具有同一个请求
        if (TradeManager.checkTradeRepeat(sender.getName(), receiver.getName())) {
            sender.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-send-fail").replace("%receiver_name%",receiver.getName())));
            return;
        }

        TradeManager.getTradeList().add(new Trade(sender, receiver));

        MessageUtils.textCanClick(sender,SafeTradeYwY.getLang().get("player-send-successful-self").replace("%receiver_name%",receiver.getName()),"/trade cancel "+receiver.getName(),SafeTradeYwY.getLang().get("hover-cancel"));
        receiver.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-send-successful-other").replace("%sender_name%",sender.getName()).replace("%s%",MainConfig.getCancelSecond().toString())));
        MessageUtils.textCanClick(receiver,SafeTradeYwY.getLang().get("hover-accept"), "/trade accept "+sender.getName(),SafeTradeYwY.getLang().get("hover-accept"));
        MessageUtils.textCanClick(receiver,SafeTradeYwY.getLang().get("hover-reject"), "/trade reject "+sender.getName(),SafeTradeYwY.getLang().get("hover-reject"));
    }
}
