package de.marylieh.simpleperms.commands

import de.marylieh.simpleperms.groups.GroupManager
import de.marylieh.simpleperms.utils.Config
import de.marylieh.simpleperms.Main.Companion.error
import de.marylieh.simpleperms.Main.Companion.prefix
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UnsetPlayerGroupCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.player.remove")) {
            player.sendMessage("${error}You don't have the Permission to do that.")
            return true
        }
        if (args.size != 2) {
            player.sendMessage("${error}Please use: §9/spgplrem <player> <group>")
            return true
        }
        val userName = args[0]
        val group = args[1]
        val user = Bukkit.getPlayer(userName)
        if (!GroupManager.getGroup(group)) {
            player.sendMessage("${error}The Group §4" + group + "§f doesn't exists.")
            return true
        }
        if (user == null) {
            player.sendMessage("${prefix}The Player §4" + userName + " §fisn't online.")
            return true
        }
        if (!GroupManager.getPlayerGroup(user, group)) {
            player.sendMessage("${error}The Player §4" + userName + "§f aren't in the Group: §4" + group)
            return true
        }
        val tempPermissionList = Config.getConfig().getList("Groups.$group.permissions") as List<String>
        for (n in tempPermissionList.indices) {
            val permission = tempPermissionList[n]
            println(permission)
            val attachment = user.addAttachment(de.marylieh.simpleperms.Main.instance)
            attachment.unsetPermission(permission)
        }
        GroupManager.removePlayerFromGroup(user, group)
        user.kickPlayer("$prefix§2You're Group has been updated!")
        player.sendMessage("$prefix§aYou have successfully §cremoved §6$userName §afrom Group: §b$group")
        return true
    }
}