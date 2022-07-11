package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.Main.Companion.error
import de.***REMOVED***.simpleperms.Main.Companion.prefix
import de.***REMOVED***.simpleperms.permissions.PermissionManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemovePermissionCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.remove")) {
            player.sendMessage("${error}You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage("${error}Please use: §9/spremove <user> <permission>")
            return true
        }
        val userName = args[0]
        val user = Bukkit.getPlayer(userName)
        val perm = args[1]
        if (user == null) {
            player.sendMessage("${error}The player is not Online.")
            return true
        }
        if (!user.hasPermission(perm)) {
            player.sendMessage("${error}The player hasn't the permission not yet")
            return true
        }
        PermissionManager.removePermission(user, perm)
        PermissionManager.removeSavedPlayerPermissions(user, perm)
        player.sendMessage("$prefix§aYou have successfully removed the permission: §6$perm §afrom: §6$userName")
        return true
    }
}