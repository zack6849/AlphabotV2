package com.zack6849.alphabot.api;

import org.pircbotx.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Group {
    private String name;
    private List<String> permissions;
    private boolean exec;
    private List<Group> inheritance;
    private HashMap<User, String> users;

    public Group(String name, List<String> permissions, boolean exec) {
        this.setName(name);
        this.setPermissions(permissions);
        this.setExec(exec);
        this.setInheritance(new ArrayList<Group>());
        this.users = new HashMap<User, String>();
    }

    public Group(String name, List<String> permissions, boolean exec, List<Group> inheritance) {
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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasExec() {
        return exec;
    }

    public void setExec(boolean exec) {
        this.exec = exec;
    }

    public List<Group> getInheritance() {
        return inheritance;
    }

    public void setInheritance(List<Group> inheritance) {
        this.inheritance = inheritance;
    }

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission) || this.permissions.contains("commands.*");
    }

    public void addPermission(String permission) {
        if (!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
    }

    public boolean addInherit(Group inherit) {
        for (String permission : inherit.getPermissions()) {
            this.addPermission(permission);
        }
        return this.inheritance.add(inherit);
    }

    public boolean removeInherit(Group inherit) {
        for (String permission : inherit.getPermissions()) {
            this.removePermission(permission);
        }
        return this.inheritance.remove(inherit);
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
