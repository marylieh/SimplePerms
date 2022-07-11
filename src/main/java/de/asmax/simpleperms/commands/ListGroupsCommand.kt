package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.Main.Companion.error
import de.***REMOVED***.simpleperms.Main.Companion.prefix
import de.***REMOVED***.simpleperms.groups.GroupManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ListGroupsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.list")) {
            player.sendMessage(error + "You don't have the Permission to do that.")
            return true
        }
        if (args.isNotEmpty()) {
            player.sendMessage(error + "Please use: §9/spglist")
            return true
        }
        val groups = GroupManager.listGroups()
        player.sendMessage("$prefix§aGroups: §6$groups")
        return true
    }
}