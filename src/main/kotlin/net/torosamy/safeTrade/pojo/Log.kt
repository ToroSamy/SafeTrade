package net.torosamy.safeTrade.pojo



class Log(val giveToSenderKit: List<String>, val giveToReceiverKit: List<String>, val date: String) {
    fun getGiveToSenderKitInfo(): String {
        val builder = StringBuilder()
        for (info in giveToSenderKit) {
            builder.append(info).append("\n")
        }
        return builder.toString()
    }

    fun getGiveToReceiverKitInfo(): String {
        val builder = StringBuilder()
        for (info in giveToReceiverKit) {
            builder.append(info).append("\n")
        }
        return builder.toString()
    }

    fun getLogInfo(): String {
        return date + "\nsender:\n"+getGiveToSenderKitInfo()+"receiver:\n"+getGiveToReceiverKitInfo()
    }
}