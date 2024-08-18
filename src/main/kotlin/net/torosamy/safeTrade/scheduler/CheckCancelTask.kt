package net.torosamy.safeTrade.scheduler

import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.pojo.Trade
import org.bukkit.scheduler.BukkitRunnable

class CheckCancelTask(private var cancelSecond: Int, private val trade: Trade) : BukkitRunnable() {
    override fun run() {
        //如果交易已经被处理过
        if(this.trade.isHandled) {
            this.cancel()
            return
        }

        //还未被处理则继续监听
        //如果交易已经超时
        if(this.cancelSecond <= 0) {
            TradeManager.removeTrade(this.trade)
            this.cancel()
            return
        }

        this.cancelSecond--
    }
}