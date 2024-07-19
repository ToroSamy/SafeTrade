package net.torosamy.safetradeywy.pojo.manager;

import net.torosamy.safetradeywy.pojo.trade.Trade;

import java.util.ArrayList;
import java.util.List;


public class TradeManager {
    private static List<Trade> tradeList = new ArrayList<>();


    public static String showAllTrade() {
        String res = "";
        for (Trade trade : tradeList) {
            res += trade.getSender().getName();
            res += " ";
            res += trade.getReceiver().getName();
            res += "\n";
        }
        return res;
    }

    public static Integer findOnTrade(String name) {
        for (int i = 0; i < tradeList.size(); i++) {
            if (!tradeList.get(i).getConfirmRequest()) continue;
            if (tradeList.get(i).getSender().getName().equalsIgnoreCase(name)) return i;
            if(tradeList.get(i).getReceiver().getName().equalsIgnoreCase(name)) return i;
        }
        return -1;
    }

    public static List<Trade> getTradeList() {
        return tradeList;
    }

    public static Integer findBySender(String name) {
        for (int i = 0; i < tradeList.size(); i++) {
            if (tradeList.get(i).getSender().getName().equalsIgnoreCase(name)) return i;
        }
        return -1;
    }

    public static void removeAllBySender(String name) {
        int index = findBySender(name);
        while (index != -1) {
            tradeList.remove(index);
            index = findBySender(name);
        }
    }

    public static Integer findByReceiver(String name) {
        for (int i = 0; i < tradeList.size(); i++) {
            if (tradeList.get(i).getReceiver().getName().equalsIgnoreCase(name)) return i;
        }
        return -1;
    }


    public static void removeAllByReceiver(String name) {
        int index = findByReceiver(name);
        while (index != -1) {
            tradeList.remove(index);
            index = findByReceiver(name);
        }
    }

    public static Integer findBySenderAndReceiver(String sender, String receiver) {
        for (int i = 0; i < tradeList.size(); i++) {
            if (tradeList.get(i).getReceiver().getName().equalsIgnoreCase(receiver) && tradeList.get(i).getSender().getName().equalsIgnoreCase(sender))
                return i;
        }
        return -1;
    }

    public static Boolean checkTradeRepeat(String name1, String name2) {
        for (int i = 0; i < tradeList.size(); i++) {
            if (tradeList.get(i).getReceiver().getName().equalsIgnoreCase(name1) && tradeList.get(i).getSender().getName().equalsIgnoreCase(name2))
                return true;
            if (tradeList.get(i).getReceiver().getName().equalsIgnoreCase(name2) && tradeList.get(i).getSender().getName().equalsIgnoreCase(name1))
                return true;
        }
        return false;

    }


    public static void removeByIndex(int index) {
        if (index == -1) return;
        tradeList.remove(index);
    }
}
