package net.torosamy.safeTrade.listener

import net.torosamy.safeTrade.utils.CommandUtil
import net.torosamy.safeTrade.utils.ConfigUtil
import net.torosamy.safeTrade.utils.HoverUtil
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

class InteractEntityListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val sender = event.player

        if (!sender.hasPermission("safetrade.send")) return

        if (!(ConfigUtil.mainConfig.sneakMode && sender.isSneaking)) return

        if (event.hand == EquipmentSlot.HAND) return

        if (event.rightClicked !is Player) return

        val receiver = event.rightClicked as Player

        CommandUtil.PLAYER_COMMANDS.sendTrade(sender, receiver)
    }

}