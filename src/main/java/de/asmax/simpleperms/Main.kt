package de.***REMOVED***.simpleperms

import de.***REMOVED***.simpleperms.commands.*
import de.***REMOVED***.simpleperms.groups.GroupManager
import de.***REMOVED***.simpleperms.listener.JoinListener
import de.***REMOVED***.simpleperms.permissions.PermissionManager
import de.***REMOVED***.simpleperms.utils.Config
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var instance: Main
        private set
        const val prefix = "§8[§9SimplePerms§8] §7"
        const val error = "§8[§4ERROR§8] §f"
    }

    override fun onLoad() {
        instance = this
        Config.Config()
    }

    override fun onEnable() {
        CommandRegistration()
        ListenerRegistration()
        GroupManager.copyDefaultGroup()
        initPlayerPermissions()
        initPlayerGroups()
    }

    override fun onDisable() {
        Config.save()
    }

    private fun CommandRegistration() {
        getCommand("spadd")!!.setExecutor(AddPermissionCommand())
        getCommand("spremove")!!.setExecutor(RemovePermissionCommand())
        getCommand("sptemp")!!.setExecutor(AddTempPermission())
        getCommand("spget")!!.setExecutor(GetPermissionCommand())
        getCommand("spgadd")!!.setExecutor(AddGroupCommand())
        getCommand("spgpadd")!!.setExecutor(AddGroupPermissionCommand())
        getCommand("spgprem")!!.setExecutor(RemoveGroupPermissionCommand())
        getCommand("spgdel")!!.setExecutor(DeleteGroupCommand())
        getCommand("spglist")!!.setExecutor(ListGroupsCommand())
        getCommand("spgplist")!!.setExecutor(ListPermissionsCommand())
        getCommand("spgpladd")!!.setExecutor(SetPlayerGroupCommand())
        getCommand("spgplrem")!!.setExecutor(UnsetPlayerGroupCommand())
        getCommand("spgplget")!!.setExecutor(GetPlayerByGroupCommand())
        getCommand("spgpllist")!!.setExecutor(ListPlayerInGroupCommand())
    }

    private fun ListenerRegistration() {
        val pluginManager = Bukkit.getPluginManager()

        //pluginManager.registerEvents(new OpCommand(), this);
        pluginManager.registerEvents(JoinListener(), this)
    }

    private fun initPlayerPermissions() {
        Bukkit.getOnlinePlayers().forEach { player: Player? ->
            if (player != null) {
                PermissionManager.revokePermissions(player)
            }
        }
    }

    private fun initPlayerGroups() {
        Bukkit.getOnlinePlayers().forEach { player: Player? ->
            if (player != null) {
                PermissionManager.revokeGroups(player)
            }
        }
    }
}