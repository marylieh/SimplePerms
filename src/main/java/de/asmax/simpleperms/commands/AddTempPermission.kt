package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.permissions.PermissionManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddTempPermission : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${Main.error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.add.temp")) {
            player.sendMessage("${Main.error}You don't have the Permission to do that.")
            return true
        }
        if (args.size != 3) {
            player.sendMessage("${Main.error}Please use: §9/sptemp <user> <permission> <time>")
            return true
        }
        val userName = args[0]
        val user = Bukkit.getPlayer(userName)
        val perm = args[1]
        val time: Long
        if (user == null) {
            player.sendMessage("${Main.error}The player is not Online.")
            return true
        }
        if (user.hasPermission(perm)) {
            player.sendMessage("${Main.error}The Player has already the permission: §4" + perm)
            return true
        }
        try {
            time = args[2].toLong()
            PermissionManager.setTempPermission(user, perm, time)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            player.sendMessage("${Main.error}Please use a NUMBER for the TIME")
        }
        return true
    }
}