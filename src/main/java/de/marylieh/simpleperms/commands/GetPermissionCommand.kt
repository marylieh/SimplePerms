package de.marylieh.simpleperms.commands

import de.marylieh.simpleperms.permissions.PermissionManager
import de.marylieh.simpleperms.Main.Companion.error
import de.marylieh.simpleperms.Main.Companion.prefix
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetPermissionCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.get")) {
            player.sendMessage(error + "You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage(error + "Please use: §9/spget <user> <permission>")
            return true
        }
        val userName = args[0]
        val user = Bukkit.getPlayer(userName)
        val perm = args[1]
        if (user == null) {
            player.sendMessage(error + "The player is not Online.")
            return true
        }
        val havePermission: Boolean = PermissionManager.getPermission(user, perm)
        if (havePermission) {
            player.sendMessage("$prefix§aThe player: §6$userName §a§lhas §r§athe Permission: §6$perm")
        } else {
            player.sendMessage("$prefix§aThe player: §6$userName §a§lhasn't §r§athe Permission: §6$perm")
        }
        return true
    }
}