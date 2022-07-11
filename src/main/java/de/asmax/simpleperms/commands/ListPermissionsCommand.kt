package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.Main.Companion.error
import de.***REMOVED***.simpleperms.Main.Companion.prefix
import de.***REMOVED***.simpleperms.groups.GroupManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ListPermissionsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.permissions.list")) {
            player.sendMessage(error + "You don't have the Permission to do that.")
            return true
        }
        if (args.size != 1) {
            player.sendMessage(error + "Please use: §9/spgplist <group>")
            return true
        }
        val name = args[0]
        if (!GroupManager.getGroup(name)) {
            player.sendMessage(error + "The group §4" + name + " §fdoesn't exists.")
            return true
        }
        val groupPermissions = GroupManager.listPermissions(name)
        player.sendMessage("$prefix§aPermissions from §9$name§a: §6$groupPermissions")
        return true
    }
}