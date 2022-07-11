package de.***REMOVED***.simpleperms.commands

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.Main.Companion.error
import de.***REMOVED***.simpleperms.Main.Companion.prefix
import de.***REMOVED***.simpleperms.groups.GroupManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class ListPlayerInGroupCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${error}Just a Player can execute this command.")
            return true
        }
        val player = sender
        if (!player.hasPermission("simpleperms.groups.player.list")) {
            player.sendMessage("${error}You don't have the Permission to do that.")
            return true
        }
        if (args.size == 0) {
            player.sendMessage(error + "Please use: §9/spgpllist <group>")
            return true
        } else if (args.size == 2 && args[1].equals("offlinemode", ignoreCase = true)) {
            val group = args[0]
            if (!GroupManager.getGroup(group)) {
                player.sendMessage(error + "The Group §4" + group + "§f doesn't exists.")
                return true
            }
            val playerList = GroupManager.listPlayers(group) as ArrayList<String?>
            player.sendMessage("$prefix§aIn the group: §b$group §aare the following Players: §6$playerList")
            return true
        }
        val group = args[0]
        if (!GroupManager.getGroup(group)) {
            player.sendMessage(error + "The Group §4" + group + "§f doesn't exists.")
            return true
        }
        val playerList = GroupManager.listPlayers(group) as ArrayList<String?>
        val playerListDecompiled = ArrayList<String>()
        var offline = 0
        for (i in playerList.indices) {
            val playerUUID = playerList[i]
            if (!playerUUID.equals("DoNotTouchThis", ignoreCase = true)) {
                val uuid = UUID.fromString(playerUUID)
                val user = Bukkit.getPlayer(uuid)
                if (user == null) {
                    offline++
                    break
                }
                val name = user.name
                playerListDecompiled.add(name)
            }
        }
        player.sendMessage("$prefix§aIn the group: §b$group §aare the following Players: §6$playerListDecompiled")
        if (offline != 0) {
            player.sendMessage("§7[NOTE] §8§o$offline players are offline, try: §r§7/spgpllist <group> offlinemode, to get the raw uuid's.")
        }
        return true
    }
}