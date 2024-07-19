package net.torosamy.safetradeywy.pojo.manager;

import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.pojo.trade.Trade;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogsManager {
    public static void addLog(Trade trade) throws IOException {
//        List<String> sendedList = trade.getSenderKitNameList();
//        List<String> receivedList = trade.getReceiverKitNameList();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = format.format(new Date());
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        YamlConfiguration logs = SafeTradeYwY.getLogs();
        logs.set(format+".sender_"+trade.getSender().getName(),trade.getSenderKitNameList());
        logs.set(format+".receiver_"+trade.getReceiver().getName(),trade.getReceiverKitNameList());
        logs.save(new File(SafeTradeYwY.getPlugin().getDataFolder(),"logs.yml"));
        SafeTradeYwY.setLogs(logs);
    }
}
