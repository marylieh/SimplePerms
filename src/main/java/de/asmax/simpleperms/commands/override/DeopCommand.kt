package de.***REMOVED***.simpleperms.commands.override

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.permissions.PermissionManager
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
            player.sendMessage("${Main.prefix}§4You have been successfully deoped!")
        }
    }
}