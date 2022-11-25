package de.marylieh.simpleperms.commands

import de.marylieh.simpleperms.groups.GroupManager
import de.marylieh.simpleperms.Main.Companion.error
import de.marylieh.simpleperms.Main.Companion.prefix
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DeleteGroupCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.delete")) {
            player.sendMessage(error + "You don't have the Permission to do that.")
            return true
        }
        if (args.size != 1) {
            player.sendMessage(error + "Please use: §9/spgdel <name>")
            return true
        }
        val name = args[0]
        if (!GroupManager.getGroup(name)) {
            player.sendMessage(error + "The group §4" + name + " §fdoesn't exists.")
            return true
        }
        GroupManager.removeGroup(name)
        player.sendMessage("$prefix§aYou have successfully §cdeleted §athe group: §6$name")
        return true
    }
}