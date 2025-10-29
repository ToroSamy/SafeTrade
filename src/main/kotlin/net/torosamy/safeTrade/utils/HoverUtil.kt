package net.torosamy.safeTrade.utils


import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player

class HoverUtil {
    companion object {
        fun createCommandHover(format: String, command: String, hover: String): TextComponent {
            var message = TextComponent(MessageUtil.format(format))
            message.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
            message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder(MessageUtil.format(hover)).create())
            return message
        }

        fun sendCommandHover(player: Player, commandHover: TextComponent) {
            player.spigot().sendMessage(commandHover)
        }

        fun replacePapi(format: String, senderName: String, receiverName: String): String {
            return MessageUtil.format(format
                .replace("%sender_name%", senderName)
                .replace("%receiver_name%", receiverName)
                .replace("%s%", ConfigUtil.mainConfig.cancelSecond.toString()))
        }
        fun replacePapi(format: String, receiverName: String): String {
            return MessageUtil.format(format
                .replace("%receiver_name%", receiverName)
                .replace("%s%", ConfigUtil.mainConfig.cancelSecond.toString()))
        }
    }
}