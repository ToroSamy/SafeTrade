package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.commands.AdminCommands
import net.torosamy.safeTrade.commands.PlayerCommands
import net.torosamy.torosamyCore.TorosamyCore

class CommandUtil {
    companion object {
        private var torosamyCorePlugin: TorosamyCore = SafeTrade.plugin.server.pluginManager.getPlugin("TorosamyCore") as TorosamyCore
        fun registerCommand() {
            torosamyCorePlugin.getCommandManager().annotationParser.parse(AdminCommands())
            torosamyCorePlugin.getCommandManager().annotationParser.parse(PlayerCommands())
        }
    }
}