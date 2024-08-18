package net.torosamy.safeTrade.manager
import net.torosamy.safeTrade.pojo.BlackList

class BlackListManager() {
    companion object {
        val lists = ArrayList<BlackList>()

        fun addBlackList(blackList: BlackList):Boolean {
//            val index = getListIndex(blackList.player)
//            if (index == -1) return false
            lists.add(blackList)
            return true
        }


        fun checkRepeat(index:Int,needBlack: String):Boolean {
            if (index == -1) return false

            val blackList: BlackList = lists.get(index)
            for (black in blackList.list) {
                if (black == needBlack) return true
            }
            return false
        }


        fun checkRepeat(owner: String, needBlack: String): Boolean {
            val index = getListIndex(owner)
            if (index == -1) return false

            val blackList: BlackList = lists.get(index)
            for (black in blackList.list) {
                if (black == needBlack) return true
            }
            return false
        }

        fun getListIndex(owner: String): Int {
            for ((index, list) in lists.withIndex()) {
                if (list.player == owner) return index
            }
            return -1;
        }
    }
}