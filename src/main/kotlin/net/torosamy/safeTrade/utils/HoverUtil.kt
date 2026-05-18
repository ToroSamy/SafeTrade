package net.torosamy.safeTrade.utils


import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player

object HoverUtil {
    fun replaceExpired(message: String): String {
        return message.replace("%expired%".toRegex(), ConfigUtil.mainConfig.cancelSecond.toString())
    }

    fun getComponent(message: String, hoverMessage: String, command: String): TextComponent {
        val textComponent = TextComponent(message)

        textComponent.hoverEvent =
            HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(hoverMessage))

        textComponent.clickEvent =
            ClickEvent(ClickEvent.Action.RUN_COMMAND, command)

        return textComponent
    }
}