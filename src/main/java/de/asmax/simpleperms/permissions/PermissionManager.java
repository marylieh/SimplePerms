package de.***REMOVED***.simpleperms.permissions;

import de.***REMOVED***.simpleperms.Main;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class PermissionManager {

    public void setPermission(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        attachment.setPermission(permission, true);
    }

    public void setTempPermission(Player player, String permission, long time) {
        long now = System.currentTimeMillis();
        long dif = (time -now) / 1000;
        int availible = (int) dif*20;
        PermissionAttachment attachment = player.addAttachment(Main.getInstance(), availible);
        attachment.setPermission(permission, false);
    }

    public void removePermission(Player player, String permission) {
        PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        attachment.setPermission(permission, false);
    }

    public boolean getPermission(Player player, String permission) {
        if(player.hasPermission(permission)) {
            return true;
        } else {
            return false;
        }
    }

}
