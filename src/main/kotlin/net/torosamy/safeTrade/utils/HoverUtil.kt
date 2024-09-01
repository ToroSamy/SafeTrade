package net.torosamy.safeTrade.utils


import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player

class HoverUtil {
    companion object {
        fun createCommandHover(text: String, command: String, hover: String): TextComponent {
            var message = TextComponent(MessageUtil.text(text))
            message.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
            message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder(MessageUtil.text(hover)).create())
            return message
        }

        fun sendCommandHover(player: Player, commandHover: TextComponent) {
            player.spigot().sendMessage(commandHover)
        }

        fun replacePapi(text: String, senderName: String, receiverName: String): String {
            return MessageUtil.text(text
                .replace("%sender_name%", senderName)
                .replace("%receiver_name%", receiverName)
                .replace("%s%", ConfigUtil.mainConfig.cancelSecond.toString()))
        }
        fun replacePapi(text: String, receiverName: String): String {
            return MessageUtil.text(text
                .replace("%receiver_name%", receiverName)
                .replace("%s%", ConfigUtil.mainConfig.cancelSecond.toString()))
        }
    }
}