/*
 *    This file is part of Alphabot.
 *
 *    Alphabot is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.zack6849.alphabot.api;

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
