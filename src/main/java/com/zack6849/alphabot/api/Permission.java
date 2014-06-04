package com.zack6849.alphabot.api;

/**
 * Created by Zack on 4/27/14.
 */
public class Permission {
    private String permission;
    private boolean inheirited;

    /**
     * @param permission  the permission node to store
     * @param isInherited if the permission node is inherited from another group, then this needs to be true.
     */
    public Permission(String permission, boolean isInherited) {
        this.setPermission(permission);
        this.setInheirited(isInherited);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isInheirited() {
        return inheirited;
    }

    public void setInheirited(boolean inheirited) {
        this.inheirited = inheirited;
    }
}
