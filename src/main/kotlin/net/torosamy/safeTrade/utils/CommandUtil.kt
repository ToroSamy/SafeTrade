package net.torosamy.safeTrade.utils

import net.torosamy.safeTrade.SafeTrade
import net.torosamy.safeTrade.commands.AdminCommands
import net.torosamy.safeTrade.commands.PlayerCommands
import net.torosamy.torosamyCore.commands.CommandManager

object CommandUtil {
    private val commanderManager: CommandManager = CommandManager(SafeTrade.plugin)

    public val ADMIN_COMMANDS: AdminCommands = AdminCommands();
    public val PLAYER_COMMANDS: PlayerCommands = PlayerCommands();

    fun registerCommand() {
        commanderManager.annotationParser.parse(ADMIN_COMMANDS)
        commanderManager.annotationParser.parse(PLAYER_COMMANDS)
    }
}