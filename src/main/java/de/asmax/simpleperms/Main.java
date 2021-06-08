package de.***REMOVED***.simpleperms;

import de.***REMOVED***.simpleperms.commands.AddPermissionCommand;
import de.***REMOVED***.simpleperms.commands.AddTempPermission;
import de.***REMOVED***.simpleperms.commands.RemovePermissionCommand;
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

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        CommandRegistration();
        ListenerRegistration();

        permissionManager = new PermissionManager();
    }

    @Override
    public void onDisable() {
        config.save();
    }

    private void CommandRegistration() {
        getCommand("spadd").setExecutor(new AddPermissionCommand());
        getCommand("spremove").setExecutor(new RemovePermissionCommand());
        getCommand("sptemp").setExecutor(new AddTempPermission());
    }

    private void ListenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
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
}
