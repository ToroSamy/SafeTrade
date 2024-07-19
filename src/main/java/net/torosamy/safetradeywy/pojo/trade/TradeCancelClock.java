package net.torosamy.safetradeywy.pojo.trade;


import net.torosamy.safetradeywy.pojo.MainConfig;
import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import org.bukkit.scheduler.BukkitRunnable;

public class TradeCancelClock extends BukkitRunnable {

    private Integer cancelSecond;
    private final Trade trade;
    public TradeCancelClock(Trade trade){
        this.trade = trade;
        this.cancelSecond = MainConfig.getCancelSecond();
    }

    @Override
    public void run() {
        if (!this.trade.getConfirmRequest()) {
            if (this.cancelSecond <= 0) {
                TradeManager.getTradeList().remove(trade);
                this.cancel();
                return;
            }
            this.cancelSecond--;
            return;
        }

        this.cancel();

    }
}
