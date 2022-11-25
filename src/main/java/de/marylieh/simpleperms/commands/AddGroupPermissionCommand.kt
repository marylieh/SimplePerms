package de.marylieh.simpleperms.commands

import de.marylieh.simpleperms.groups.GroupManager
import de.marylieh.simpleperms.Main.Companion.error
import de.marylieh.simpleperms.Main.Companion.prefix
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddGroupPermissionCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.permission.add")) {
            player.sendMessage(error + "You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage(error + "Please use: §9/spgpadd <group> <permission>")
            return true
        }
        val name = args[0]
        val permission = args[1]
        if (!GroupManager.getGroup(name)) {
            player.sendMessage(error + "The group §4" + name + " §fdoesn't exists.")
            return true
        }
        GroupManager.addPermission(name, permission)
        player.sendMessage("$prefix§aYou have successfully §2added §athe permission: §6$permission §ato the Group: §6$name")
        return true
    }
}