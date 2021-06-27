package de.***REMOVED***.simpleperms;

import de.***REMOVED***.simpleperms.commands.*;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import de.***REMOVED***.simpleperms.listener.JoinListener;
import de.***REMOVED***.simpleperms.permissions.PermissionManager;
import de.***REMOVED***.simpleperms.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private String prefix = "§8[§9SimplePerms§8] §7";
    private String error = "§8[§4ERROR§8] §f";
    private Config config;
    private PermissionManager permissionManager;
    private GroupManager groupManager;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        CommandRegistration();
        ListenerRegistration();
        GroupManager.copyDefaultGroup();

        permissionManager = new PermissionManager();
        groupManager = new GroupManager();
        initPlayerPermissions();
        initPlayerGroups();
    }

    @Override
    public void onDisable() {
        config.save();
    }

    private void CommandRegistration() {
        getCommand("spadd").setExecutor(new AddPermissionCommand());
        getCommand("spremove").setExecutor(new RemovePermissionCommand());
        getCommand("sptemp").setExecutor(new AddTempPermission());
        getCommand("spget").setExecutor(new GetPermissionCommand());
        getCommand("spgadd").setExecutor(new AddGroupCommand());
        getCommand("spgpadd").setExecutor(new AddGroupPermissionCommand());
        getCommand("spgprem").setExecutor(new RemoveGroupPermissionCommand());
        getCommand("spgdel").setExecutor(new DeleteGroupCommand());
        getCommand("spglist").setExecutor(new ListGroupsCommand());
        getCommand("spgplist").setExecutor(new ListPermissionsCommand());
        getCommand("spgpladd").setExecutor(new SetPlayerGroupCommand());
        getCommand("spgplrem").setExecutor(new UnsetPlayerGroupCommand());
        getCommand("spgplget").setExecutor(new GetPlayerByGroupCommand());
        getCommand("spgpllist").setExecutor(new ListPlayerInGroupCommand());
    }

    private void ListenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        //pluginManager.registerEvents(new OpCommand(), this);
        pluginManager.registerEvents(new JoinListener(), this);
    }

    private void initPlayerPermissions() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player != null) {
                permissionManager.revokePermissions(player);
            }
        });
    }

    private void initPlayerGroups() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player != null) {
                permissionManager.revokeGroups(player);
            }
        });
    }

    public static Main getInstance() {
        return instance;
    }

    public Config getConfiguration() {
        return config;
    }

    public String getError() {
        return error;
    }

    public String getPrefix() {
        return prefix;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

}
