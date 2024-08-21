package net.torosamy.safeTrade.manager

import net.torosamy.safeTrade.pojo.Log
import java.util.*

class LogManager {
    companion object {
        var logs = ArrayList<Log>()

        fun addLog(log: Log) {
            logs.add(log)
        }

        fun getLogsInfo(): ArrayList<String> {
            val info = ArrayList<String>()
            for (log in logs) { info.add(log.getLogInfo()) }
            return info
        }


    }
}