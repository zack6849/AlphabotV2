/*
 *  This file is part of Alphabot.
 *
 *  Alphabot is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Alphabot is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Alphabot.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.zack6849.alphabot.api;

import com.notoriousdev.yamlconfig.YamlConfig;
import com.notoriousdev.yamlconfig.configuration.MemoryConfigurationSection;
import com.notoriousdev.yamlconfig.configuration.file.FileConfiguration;
import org.pircbotx.User;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionManager {

    private Config configs;
    private FileConfiguration config;

    public PermissionManager(Config conf) {
        this.configs = conf;
    }

    private final List<Group> groups = new LinkedList<>();

    public void load() {
        try {
            YamlConfig conf = new YamlConfig(new File("").getAbsoluteFile(), "groups.yml");
            conf.saveDefaultConfig();
            FileConfiguration config = conf.getConfig();
            this.config = config;
            System.out.println("Groups file located at: " + conf.getConfigFile().getAbsoluteFile().getAbsolutePath());
            MemoryConfigurationSection section = (MemoryConfigurationSection) config.get("groups");
            for (String name : section.getKeys(false)) {
                List<String> permissions = config.getStringList(String.format("groups.%s.permissions", name));
                boolean canExec = config.getBoolean(String.format("groups.%s.exec", name));
                List<String> inherit = config.getStringList(String.format("groups.%s.inheritance", name));
                groups.add(new Group(name, permissions, canExec));
            }
            for (Group group : groups) {
                List<String> inherit = config.getStringList(String.format("groups.%s.inheritance", group.getName()));
                for (String in : inherit) {
                    Group ing = getGroupByName(in);
                    group.addInherit(ing);
                    for (String perm : ing.getPermissions()) {
                        group.addPermission(perm);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PermissionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Group getGroupByName(String id) {
        boolean found = false;
        for (Group group : groups) {
            if (group.getName().equalsIgnoreCase(id)) {
                found = true;
                return group;
            }
        }
        Logger.getGlobal().log(Level.SEVERE, "Attempted to get invalid group " + id + "!");
        return null;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public PermissionManager getPermissionsManager() {
        return this;
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            load();
            ;
        }
        return this.config;
    }

    public Group getUserGroup(User user) {
        FileConfiguration config = this.getConfig();
        if (config == null) {
            System.out.println("Config was null D:");
        }
        boolean hostmatch = false;
        String nickname = user.getNick();
        String hostmask = user.getHostmask();
        for (String mask : config.getStringList("users")) {
            nickname = mask.split("::")[0].split("\\@")[0];
            Pattern p = Pattern.compile(nickname.replaceAll("\\*", ".*"));
            Matcher m = p.matcher(nickname);
            //dont even bother wasting the time on checking the hostmask if the name isnt a match.
            if (m.find()) {
                hostmask = mask.split("::")[0].split("\\@")[1];
                p = Pattern.compile(hostmask.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*"));
                m = p.matcher(hostmask);
                if (m.find()) {
                    Group target = getGroupByName(mask.split("::")[1]);
                    target.addUser(user);
                    return target;
                }
            }
        }
        return getGroupByName("Default");
    }
}
