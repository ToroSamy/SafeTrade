package net.torosamy.safetradeywy.commands;

import net.torosamy.safetradeywy.SafeTradeYwY;
import net.torosamy.safetradeywy.utils.DataUtils;
import net.torosamy.safetradeywy.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if (!"tradeywy".equalsIgnoreCase(label)) return true;
        if (strings.length != 0) {
            commandSender.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("input-strings-error")));
            return true;
        }
        if (commandSender instanceof Player) {
            if (!commandSender.hasPermission("safetradeywy.reload")) {
                commandSender.sendMessage(MessageUtils.text("no-permission"));
                return true;
            }
        }


        DataUtils.reloadConfig();
        commandSender.sendMessage(MessageUtils.text(SafeTradeYwY.getLang().get("reload-successful")));

        return true;
    }
}
