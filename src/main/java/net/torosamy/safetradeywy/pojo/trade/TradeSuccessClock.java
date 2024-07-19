package net.torosamy.safetradeywy.pojo.trade;

import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.pojo.MainConfig;
import net.torosamy.safetradeywy.pojo.manager.LogsManager;
import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import net.torosamy.safetradeywy.utils.MessageUtils;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;


public class TradeSuccessClock extends BukkitRunnable {
    private Trade trade;
    private Integer successSecond;
    public TradeSuccessClock(Trade trade) {
        this.trade = trade;
        this.successSecond = MainConfig.getSuccessSecond();
    }

    @Override
    public void run() {
        if (trade.getTradeInventory().getFinished()) {
            this.cancel();
            return;
        }

        if (trade.getConfirmReceiver() && trade.getConfirmSender()) {
            if (successSecond <= 0) {
                //交易完成
                trade.getTradeInventory().setFinished(true);
                trade.updateGiveToReceiverKit();
                trade.updateGiveToSenderKit();
                trade.getSender().openInventory(trade.getGiveToSenderKit());
                trade.getReceiver().openInventory(trade.getGiveToReceiverKit());

                trade.getSender().sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("trade-success")));
                trade.getReceiver().sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("trade-success")));

                if (MainConfig.getStartLogs()) {
                    try {
                        LogsManager.addLog(trade);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


                TradeManager.getTradeList().remove(trade);
                this.cancel();
                return;
            }
            trade.getTradeInventory().updateYellowConfirmItem(successSecond);
            trade.getSender().playSound(trade.getSender().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,20,20);
            trade.getReceiver().playSound(trade.getReceiver().getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,20,20);


            successSecond--;
            return;
        }
        this.successSecond = MainConfig.getSuccessSecond();
        trade.getTradeInventory().updateYellowConfirmItem(-1);
    }
}
