package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.commands.AdminCommands
import net.torosamy.safeTrade.commands.PlayerCommands
import net.torosamy.torosamyCore.TorosamyCore

class CommandUtil {
    companion object {
        fun registerCommand() {
            TorosamyCore.commanderManager.annotationParser.parse(AdminCommands())
            TorosamyCore.commanderManager.annotationParser.parse(PlayerCommands())
        }
    }
}