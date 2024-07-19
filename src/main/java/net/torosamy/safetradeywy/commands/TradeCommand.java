package net.torosamy.safetradeywy.commands;


import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.pojo.MainConfig;

import net.torosamy.safetradeywy.pojo.trade.Trade;
import net.torosamy.safetradeywy.pojo.manager.TradeManager;
import net.torosamy.safetradeywy.pojo.trade.TradeSuccessClock;
import net.torosamy.safetradeywy.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TradeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if (!"trade".equalsIgnoreCase(label)) return true;
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;

        if (!player.hasPermission("safetradeywy.use")) {
            player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-permission")));
            return true;
        }

        if (strings.length == 1) {
            if (!player.hasPermission("safetradeywy.send")) {
                player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-permission")));
                return true;
            }

            if ("check".equalsIgnoreCase(strings[0])) {
                player.sendMessage(TradeManager.showAllTrade());
                return true;
            }


            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getName().equalsIgnoreCase(strings[0])) {
                    TradeManager.getTradeList().add(new Trade(player, onlinePlayer));


                    MessageUtils.textCanClick(player,SafeTradeYwY.getLang().get("player-send-successful-self").replace("%receiver_name%",onlinePlayer.getName()),"/trade cancel "+onlinePlayer.getName(),SafeTradeYwY.getLang().get("hover-cancel"));
                    onlinePlayer.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-send-successful-other").replace("%sender_name%",player.getName()).replace("%s%",MainConfig.getCancelSecond().toString())));
                    MessageUtils.textCanClick(onlinePlayer,SafeTradeYwY.getLang().get("hover-accept"), "/trade accept "+player.getName(),SafeTradeYwY.getLang().get("hover-accept"));
                    MessageUtils.textCanClick(onlinePlayer,SafeTradeYwY.getLang().get("hover-reject"), "/trade reject "+player.getName(),SafeTradeYwY.getLang().get("hover-reject"));
                    return true;
                }
            }
            player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-find-player")));
            return true;
        }

        if (strings.length == 2) {
            if ("cancel".equalsIgnoreCase(strings[0])) {
                if (!player.hasPermission("safetradeywy.cancel")) {
                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-permission")));
                    return true;
                }
                Integer index = TradeManager.findBySenderAndReceiver(player.getName(), strings[1]);
                if (index == -1) {
                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-find-trade")));
                    return true;
                }
                TradeManager.removeByIndex(index);
                player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-cancel-successful").replace("%receiver_name%", strings[1])));
                return true;
            }

            if ("accept".equalsIgnoreCase(strings[0])) {
                if (!player.hasPermission("safetradeywy.accept")) {
                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-permission")));
                    return true;
                }
                Integer index = TradeManager.findBySenderAndReceiver(strings[1], player.getName());
                if (index == -1) {
                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-find-trade")));
                    return true;
                }

//                if (PlayerManager.contains(strings[1])) {
//                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-accept-fail-ontrade")));
//                    return true;
//                }
//
//                PlayerManager.getOnTradePlayer().put(strings[1], player.getName());

                TradeManager.getTradeList().get(index).getSender().sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-send-accept").replace("%receiver_name%", player.getName())));
                TradeManager.getTradeList().get(index).getReceiver().sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-receive-accept").replace("%sender_name%", strings[1])));

                //为两位玩家打开交易界面
                TradeManager.getTradeList().get(index).setConfirmRequest(true);
                TradeManager.getTradeList().get(index).getSender().openInventory(TradeManager.getTradeList().get(index).getTradeInventory().getInventory());
                TradeManager.getTradeList().get(index).getReceiver().openInventory(TradeManager.getTradeList().get(index).getTradeInventory().getInventory());
                new TradeSuccessClock(TradeManager.getTradeList().get(index)).runTaskTimer(SafeTradeYwY.getPlugin(),0L,20L);
                return true;
            }

            if ("reject".equalsIgnoreCase(strings[0])) {
                if (!player.hasPermission("safetradeywy.reject")) {
                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-permission")));
                    return true;
                }
                Integer index = TradeManager.findBySenderAndReceiver(strings[1], player.getName());
                if (index == -1) {
                    player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("no-find-trade")));
                    return true;
                }

                TradeManager.getTradeList().get(index).getSender().sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-send-reject").replace("%receiver_name%", player.getName())));
                TradeManager.getTradeList().get(index).getReceiver().sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("player-receive-reject").replace("%sender_name%", strings[1])));
                TradeManager.removeByIndex(index);
                return true;
            }
        }

        player.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("input-strings-error")));
        return true;

    }
}
