package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.Main.Companion.error
import de.***REMOVED***.simpleperms.Main.Companion.prefix
import de.***REMOVED***.simpleperms.groups.GroupManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveGroupPermissionCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.permission.remove")) {
            player.sendMessage("${error}You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage("${error}Please use: §9/spgpremove <group> <permission>")
            return true
        }
        val name = args[0]
        val permission = args[1]
        if (!GroupManager.getGroup(name)) {
            player.sendMessage("${error}The group §4" + name + " §fdoesn't exists.")
            return true
        }
        if (!GroupManager.getPermission(name, permission)) {
            player.sendMessage("${error}The Group: §9" + name + " §fhas not the Permission: §9" + permission)
            return true
        }
        GroupManager.removePermission(name, permission)
        player.sendMessage("$prefix§aYou have successfully §cremoved §athe permission: §6$permission §afrom the Group: §6$name")
        return true
    }
}