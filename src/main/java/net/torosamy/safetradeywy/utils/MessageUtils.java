package net.torosamy.safetradeywy.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {
    public static String text(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    public static void textCanClick(Player player, String text,String command,String hoverText){
        TextComponent message = new TextComponent(MessageUtils.text(text));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,(new ComponentBuilder(MessageUtils.text(hoverText)).create())));
        player.spigot().sendMessage((BaseComponent) message);
    }
}
