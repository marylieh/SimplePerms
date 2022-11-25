package de.marylieh.simpleperms.commands

import de.marylieh.simpleperms.Main.Companion.error
import de.marylieh.simpleperms.Main.Companion.prefix
import de.marylieh.simpleperms.groups.GroupManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddGroupCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.add")) {
            player.sendMessage(error + "You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage(error + "Please use: §9/spgadd <name> <level>")
            return true
        }
        val name = args[0]
        try {
            val level = args[1].toInt()
            if (GroupManager.getGroup(name)) {
                player.sendMessage(error + "The group §4" + name + " §falready exists.")
                return true
            }
            GroupManager.addGroup(name, level)
            player.sendMessage("$prefix§aYou have successfully created the group: §6$name")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            player.sendMessage(error + "Please use a NUMBER as 3th argument!")
        }
        return true
    }
}