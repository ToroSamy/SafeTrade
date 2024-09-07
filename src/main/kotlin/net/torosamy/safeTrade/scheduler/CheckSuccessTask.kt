package net.torosamy.safeTrade.scheduler

import net.torosamy.safeTrade.manager.LogManager
import net.torosamy.safeTrade.manager.TradeManager
import net.torosamy.safeTrade.pojo.Log
import net.torosamy.safeTrade.pojo.Trade
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable
import java.text.SimpleDateFormat
import java.util.*

class CheckSuccessTask(private var successSecond:Int,private val trade: Trade) :BukkitRunnable() {
    override fun run() {
        //如果面板的交易已经成功完成
        if(this.trade.tradeInventory.isFinished) {
            this.cancel()
            return
        }
        //如果有任何一方未同意 则不断更新保持黄色按钮 并且 设置满剩余时长
        if(!this.trade.isSenderConfirm || !this.trade.isReceiverConfirm) {
            //如果duration的时长 < 0  则认为双方未准备完成
            //如果duration的时长 >= 0 则认为双方正等待交易完成
            this.successSecond = ConfigUtil.mainConfig.continueSecond
            trade.tradeInventory.updateYellowButton(-1)
            return
        }
        //如果确认时长有剩余 则不断更新剩余时长按钮和播放声音来提醒
        if(this.successSecond > 0) {
            this.trade.tradeInventory.updateYellowButton(successSecond)
            trade.sender.playSound(trade.sender.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
            trade.receiver.playSound(trade.receiver.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
            successSecond--
            return
        }

        trade.tradeInventory.isFinished = true
        TradeManager.removeTrade(this.trade)

        trade.updateGiveToSenderKit()
        trade.updateGiveToReceiverKit()
        trade.sender.openInventory(trade.giveToSenderKit)
        trade.receiver.openInventory(trade.giveToReceiverKit)

        if (ConfigUtil.mainConfig.startLogs.enabled) {
            val log = Log(
                trade.getSenderKitItemInfo(),
                trade.getReceiverKitItemInfo(),
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            )
            LogManager.addLog(log)
            if (ConfigUtil.mainConfig.startLogs.consoleEnabled) {
               Bukkit.getConsoleSender().sendMessage(log.getLogInfo())
            }
        }


        trade.sender.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.tradeSuccess,trade.sender.name,trade.receiver.name))
        trade.receiver.sendMessage(HoverUtil.replacePapi(ConfigUtil.langConfig.tradeSuccess,trade.sender.name,trade.receiver.name))

        this.cancel()
    }
}