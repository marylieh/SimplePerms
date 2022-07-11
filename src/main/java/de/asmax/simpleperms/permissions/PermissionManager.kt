package de.***REMOVED***.simpleperms.permissions

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.groups.GroupManager
import de.***REMOVED***.simpleperms.utils.Config
import org.apache.commons.lang.StringUtils
import org.bukkit.entity.Player
import java.util.*

object PermissionManager {
    fun setPermission(player: Player, permission: String?) {
        val attachment = player.addAttachment(Main.instance)
        attachment.setPermission(permission!!, true)
    }

    fun revokePermissions(player: Player) {
        val UUID = player.uniqueId.toString()
        if (Config.getConfig()["Player.$UUID.permissions"] == null) {
            return
        }
        val permissions = Config.getConfig().getList("Player.$UUID.permissions") as List<String>
        println(permissions)
        for (i in permissions.indices) {
            val permission = permissions[i]
            println(permission)
            val attachment = player.addAttachment(Main.instance)
            attachment.setPermission(permission, true)
        }
    }

    fun revokeGroups(player: Player) {
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
                e.printStackTrace()
                println("NullPointerException in Group Management System!")
            }
        }
    }

    fun setTempPermission(player: Player, permission: String?, time: Long) {
        val now = System.currentTimeMillis()
        val dif = (time - now) / 1000
        val available = dif.toInt() * 20
        val attachment = player.addAttachment(Main.instance, available)
        attachment!!.setPermission(permission!!, false)
    }

    fun removePermission(player: Player, permission: String?) {
        val attachment = player.addAttachment(Main.instance)
        attachment.setPermission(permission!!, false)
    }

    fun getPermission(player: Player, permission: String?): Boolean {
        return player.hasPermission(permission!!)
    }

    fun savePlayerPermissions(player: Player, permission: String) {
        if (Config.getConfig()["Player." + player.uniqueId + ".permissions"] == null) {
            val tempPlayerPerms = ArrayList<String>()
            tempPlayerPerms.add("default")
            Config.getConfig()["Player." + player.uniqueId + ".permissions"] = tempPlayerPerms
            Config.save()
        }
        val playerPerms =
            Config.getConfig().getList("Player." + player.uniqueId + ".permissions") as MutableList<String>
        playerPerms.add(permission)
        Config.getConfig()["Player." + player.uniqueId + ".permissions"] = playerPerms
        Config.save()
    }

    fun removeSavedPlayerPermissions(player: Player, permission: String) {
        if (Config.getConfig()["Player." + player.uniqueId + ".permissions"] == null) {
            println("ABORT!")
            return
        }
        val playerPerms =
            Config.getConfig().getList("Player." + player.uniqueId + ".permissions") as MutableList<String>
        println(playerPerms.toString())
        playerPerms.remove(permission)
        Config.getConfig()["Player." + player.uniqueId + ".permissions"] = playerPerms
        println(playerPerms.toString())
        Config.save()
    }
}