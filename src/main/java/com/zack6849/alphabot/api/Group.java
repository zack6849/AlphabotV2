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

import com.google.gson.annotations.Expose;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Group {
    private String name;
    private List<Permission> permissions;
    private boolean exec;
    //please for the love of god do **NOT** make these non-static, it will fuck up your day.
    private List<String> inheritance;
    @Expose(serialize = false)
    private static HashMap<User, String> users;
    @Expose(serialize = false)
    private static PermissionManager manager;

    public Group(String name, List<Permission> permissions, boolean exec) {
        manager = new PermissionManager();
        this.setName(name);
        this.setPermissions(permissions);
        this.setExec(exec);
        this.setInheritance(new ArrayList<String>());
        this.users = new HashMap<User, String>();
    }

    public Group(String name, List<Permission> permissions, boolean exec, List<String> inheritance) {
        manager = new PermissionManager();
        this.setName(name);
        this.setPermissions(permissions);
        this.setExec(exec);
        this.setInheritance(inheritance);
        this.users = new HashMap<User, String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean hasExec() {
        return exec;
    }

    public void setExec(boolean exec) {
        this.exec = exec;
    }

    public List<String> getInheritance() {
        return inheritance;
    }

    public void setInheritance(List<String> inheritance) {
        this.inheritance = inheritance;
    }

    public boolean hasPermission(Permission permission) {
        for(Permission perm : permissions){
            if(perm.getPermission().equalsIgnoreCase(permission.getPermission())){
                return true;
            }
            if(perm.getPermission().equalsIgnoreCase("commands.*")){
                return true;
            }
        }
        return false;
    }

    public void addPermission(Permission permission) {
        boolean found = false;
        for(Permission perm : permissions){
            if(perm.getPermission().equals(permission.getPermission())){
               found = true;
            }
        }
        if(!found){
            permissions.add(permission);
        }
    }

    public void removePermission(String permission) {
        for(Permission perm : permissions){
            if(perm.getPermission().equalsIgnoreCase(permission)){
                this.permissions.remove(perm);
                return;
            }
        }
    }

    public void addInherit(Group toinherit) {
        for (Permission permission : toinherit.getPermissions()) {
            this.addPermission(new Permission(permission.getPermission(), true));
        }
        if (!this.inheritance.contains(toinherit.getName())) {
            this.inheritance.add(toinherit.getName());
        }
    }

    public void removeInherit(Group inherit) {
        for (Permission permission : inherit.getPermissions()) {
            this.removePermission(permission.getPermission());
        }
        if (this.inheritance.contains(inherit.getName())) {
            this.inheritance.remove(inherit.getName());
        }
    }

    public HashMap<User, String> getUsers() {
        return this.users;
    }

    public void addUser(User user, String mask) {
        if (!this.users.containsKey(user)) {
            this.users.put(user, mask);
        }
    }

    public void removeUser(User user) {
        if (this.users.containsKey(user)) {
            this.users.remove(user);
        }
    }
}
