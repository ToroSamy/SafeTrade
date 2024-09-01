package net.torosamy.safeTrade.manager

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.pojo.Trade
import net.torosamy.safeTrade.scheduler.CheckCancelTask
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import org.bukkit.entity.Player

class TradeManager {
    companion object{
        var tradeList = ArrayList<Trade>()


        fun getAllTradeInfo(): String {
            var res = ""
            for (trade in tradeList) { res += trade.sender.name + " " + trade.receiver.name +"\n" }
            return res
        }


        fun addTrade(sender: Player, receiver:Player): Boolean {
            //前置插件会检测玩家是否存在

            val senderIndex = getTradeIndex(sender.name)
            if(senderIndex != -1) {
                val message = HoverUtil.replacePapi(ConfigUtil.langConfig.sendFailSelf,sender.name,receiver.name)
                sender.sendMessage(message)
                return false
            }

            val receiverIndex = getTradeIndex(receiver.name)
            if (receiverIndex != -1) {
                val message = HoverUtil.replacePapi(ConfigUtil.langConfig.sendFailOther,sender.name,receiver.name)
                sender.sendMessage(message)
                return false
            }
            //如果发送成功 则开始监测 以便于自动删除
            val trade = Trade(sender, receiver)
            tradeList.add(trade)
            CheckCancelTask(ConfigUtil.mainConfig.cancelSecond,trade).runTaskTimer(SafeTrade.plugin,0L,20L)
            return true
        }

        fun removeTrade(index: Int):Boolean {
            if(index == -1) return false
            val trade = tradeList.get(index)
            trade.tradeInventory.isFinished = true
            tradeList.remove(trade)
            return true
        }

        fun removeTrade(name: String):Boolean {
            return removeTrade(getTradeIndex(name))
        }

        fun removeTrade(trade: Trade):Boolean {
            return removeTrade(getTradeIndex(trade.sender.name))
        }

        fun getTradeIndex(name:String):Int {
            for ((index, trade) in tradeList.withIndex()) {
                if(trade.receiver.name == name || trade.sender.name == name) return index
            }
            return -1;
        }



    }
}