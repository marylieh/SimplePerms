package de.***REMOVED***.simpleperms.listener;

import de.***REMOVED***.simpleperms.Main;
import de.***REMOVED***.simpleperms.groups.GroupManager;
import de.***REMOVED***.simpleperms.permissions.PermissionManager;
import de.***REMOVED***.simpleperms.utils.Config;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JoinListener implements Listener {

    Config config = Main.getInstance().getConfiguration();
    PermissionManager permissionManager = Main.getInstance().getPermissionManager();

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String UUID = player.getUniqueId().toString();

        if(config.getConfig().get("Player." + UUID + ".permissions") == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) config.getConfig().getList("Player." + UUID + ".permissions");

        for (int i = 0; i < permissions.size(); i++) {
            String permission = permissions.get(i);

            PermissionAttachment attachment = player.addAttachment(Main.getInstance());
            attachment.setPermission(permission, true);
        }

        //Group Management: Inherit Permissions to Player who are in a specific group

        Set<String> totalGroups = config.getConfig().getConfigurationSection("Groups").getKeys(false);
        StringBuilder groupString = new StringBuilder(totalGroups.toString());
        groupString.delete(0, 10);
        String test = groupString.toString();
        test = StringUtils.removeEnd(test, "]");
        System.out.println(test);
        test = StringUtils.deleteWhitespace(test);
        System.out.println(test);
        System.out.println(test);

        ArrayList<String> totalGroupList = new ArrayList<String>(Arrays.asList(test.split(",")));

        for(int i = 0; i < totalGroupList.size(); i++) {
            String group = totalGroupList.get(i);
            System.out.println(group);

            try {

                if(GroupManager.getPlayerGroup(player, group)) {

                    @SuppressWarnings("unchecked")
                    List<String> tempPermissionList = (List<String>) config.getConfig().getList("Groups." + group + ".permissions");

                    for (int n = 0; n < tempPermissionList.size(); n++) {
                        String permission = tempPermissionList.get(n);
                        System.out.println(permission);

                        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
                        attachment.setPermission(permission, true);

                    }

                }

            } catch (NullPointerException e) {
                System.out.println("NullPointerException in Group Management System! If the Permission System work properly you can ignore this error.");
            }

        }

    }

}
