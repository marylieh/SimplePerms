package de.***REMOVED***.simpleperms.groups

import de.***REMOVED***.simpleperms.Main
import de.***REMOVED***.simpleperms.utils.Config
import org.bukkit.entity.Player

object GroupManager {
    //TODO: Add Players to groups and inherit their permissions
    fun addGroup(group: String, level: Int) {
        val tempList = ArrayList<String>()
        val playertempList = ArrayList<String>()
        tempList.add("spacer.space")
        playertempList.add("DoNotTouchThis")
        Config.getConfig()["Groups.$group.level"] = level
        Config.getConfig()["Groups.$group.permissions"] = tempList
        Config.getConfig()["Groups.$group.players"] = playertempList
        Config.save()
    }

    fun addPlayerToGroup(player: Player?, group: String) {
        if (!getGroup(group)) {
            return
        }
        if (player == null) {
            return
        }
        val UUID = player.uniqueId.toString()
        val playerList = Config.getConfig().getList("Groups.$group.players") as MutableList<String>?
        println(playerList)
        if (playerList == null) {
            println("PlayerList equals null")
            return
        }
        playerList.add(UUID)
        Config.getConfig()["Groups.$group.players"] = playerList
        Config.save()
    }

    fun removePlayerFromGroup(player: Player, group: String) {
        val UUID = player.uniqueId.toString()
        val playerList = Config.getConfig().getList("Groups.$group.players") as MutableList<String>
        if (!playerList.contains(UUID)) {
            return
        }
        playerList.remove(UUID)
        Config.getConfig()["Groups.$group.players"] = playerList
        Config.save()
    }

    fun getPlayerGroup(player: Player, group: String): Boolean {
        val UUID = player.uniqueId.toString()
        val playerList = Config.getConfig().getList("Groups.$group.players") as List<String>
        return playerList.contains(UUID)
    }

    fun addPermission(group: String, permission: String) {
        println("[DEBUG] Group: $group Permission: $permission")
        val groupPermissionList = Config.getConfig().getList(
            "Groups.$group.permissions"
        ) as MutableList<String>
        if (Config.getConfig()["Groups.$group"] == null) {
            return
        }
        groupPermissionList.add(permission)
        Config.getConfig()["Groups.$group.permissions"] = groupPermissionList
        Config.save()
    }

    fun listPlayers(group: String): List<String> {
        return Config.getConfig().getList("Groups.$group.players") as List<String>
    }

    fun copyDefaultGroup() {
        if (Config.getConfig()["Groups.default"] != null) {
            return
        }
        Config.getConfig()["Groups.default.level"] = 1
        Config.save()
    }

    fun removePermission(group: String, permission: String) {
        val groupPermissionList = Config.getConfig().getList(
            "Groups.$group.permissions"
        ) as MutableList<String>
        if (Config.getConfig()["Groups.$group"] == null) {
            return
        }
        if (!groupPermissionList.contains(permission)) {
            return
        }
        groupPermissionList.remove(permission)
        Config.getConfig()["Groups.$group.permissions"] = groupPermissionList
        Config.save()
    }

    fun getPermission(group: String, permission: String): Boolean {
        val groupPermissionList = Config.getConfig().getList("Groups.$group.permissions") as List<String>
        if (Config.getConfig()["Groups.$group"] == null) {
            return false
        }
        return groupPermissionList.contains(permission)
    }

    fun removeGroup(name: String) {
        if (Config.getConfig()["Groups.$name"] == null) {
            return
        }
        Config.getConfig()["Groups.$name"] = null
        Config.save()
    }

    fun listGroups(): String {
        return if (Config.getConfig()["Groups"] == null) {
            "error"
        } else Config.getConfig().getConfigurationSection("Groups")?.getKeys(false).toString()
    }

    fun getGroup(name: String): Boolean {
        return Config.getConfig()["Groups.$name"] != null
    }

    fun listPermissions(group: String): String {
        if (Config.getConfig()["Groups.$group"] == null) {
            return "error"
        }
        val groupPermissionList = Config.getConfig()
            .getList("Groups.$group.permissions") as List<String>
        return groupPermissionList.toString()
    }
}