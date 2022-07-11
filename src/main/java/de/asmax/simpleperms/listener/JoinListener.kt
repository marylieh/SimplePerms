package de.***REMOVED***.simpleperms.listener

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.groups.GroupManager
import de.***REMOVED***.simpleperms.permissions.PermissionManager
import de.***REMOVED***.simpleperms.utils.Config
import org.apache.commons.lang.StringUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*

class JoinListener : Listener {
    @EventHandler
    fun handleJoin(event: PlayerJoinEvent) {
        val player = event.player
        val UUID = player.uniqueId.toString()
        if (Config.getConfig()["Player.$UUID.permissions"] == null) {
            return
        }
        val permissions = Config.getConfig().getList("Player.$UUID.permissions") as List<String>
        for (i in permissions.indices) {
            val permission = permissions[i]
            val attachment = player.addAttachment(Main.instance)
            attachment.setPermission(permission, true)
        }

        //Group Management: Inherit Permissions to Player who are in a specific group
        val totalGroups = Config.getConfig().getConfigurationSection("Groups")?.getKeys(false)
        val groupString = StringBuilder(totalGroups.toString())
        groupString.delete(0, 10)
        var test = groupString.toString()
        test = StringUtils.removeEnd(test, "]")
        println(test)
        test = StringUtils.deleteWhitespace(test)
        println(test)
        println(test)
        val totalGroupList = ArrayList(Arrays.asList(*test.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()))
        for (i in totalGroupList.indices) {
            val group = totalGroupList[i]
            println(group)
            try {
                if (GroupManager.getPlayerGroup(player, group)) {
                    val tempPermissionList = Config.getConfig().getList("Groups.$group.permissions") as List<String>
                    for (n in tempPermissionList.indices) {
                        val permission = tempPermissionList[n]
                        println(permission)
                        val attachment = player.addAttachment(Main.instance)
                        attachment.setPermission(permission, true)
                    }
                }
            } catch (e: NullPointerException) {
                println("NullPointerException in Group Management System! If the Permission System work properly you can ignore this error.")
            }
        }
    }
}