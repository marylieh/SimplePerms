package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.Main.Companion.prefix
import de.***REMOVED***.simpleperms.groups.GroupManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetPlayerGroupCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${Main.error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.player.add")) {
            player.sendMessage("${Main.error}You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage("${Main.error}Please use: §9/spgpladd <player> <group>")
            return true
        }
        val userName = args[0]
        val group = args[1]
        val user = Bukkit.getPlayer(userName)
        if (!GroupManager.getGroup(group)) {
            player.sendMessage("${Main.error}The Group §4" + group + "§f doesn't exists.")
            return true
        }
        if (user == null) {
            player.sendMessage("${prefix}The Player §4" + userName + " §fisn't online.")
            return true
        }
        if (GroupManager.getPlayerGroup(user, group)) {
            player.sendMessage("${Main.error}The Player §4" + userName + " §f is already in the Group: §b" + group)
            return true
        }
        GroupManager.addPlayerToGroup(user, group)
        user.kickPlayer("$prefix§2You're Group has been updated!")
        player.sendMessage("$prefix§aYou have successfully §2added §6$userName §ato Group: §b$group")
        return true
    }
}