package de.marylieh.simpleperms.commands.override

import de.marylieh.simpleperms.permissions.PermissionManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class DeopCommand : Listener {
    @EventHandler
    fun handleCommand(event: PlayerCommandPreprocessEvent) {
        val message = event.message
        val player = event.player
        if (message.equals("/deop", ignoreCase = true)) {
            event.isCancelled = true
            PermissionManager.removePermission(player, "*")
            PermissionManager.savePlayerPermissions(player, "*")
            player.sendMessage("${de.marylieh.simpleperms.Main.prefix}§4You have been successfully deoped!")
        }
    }
}