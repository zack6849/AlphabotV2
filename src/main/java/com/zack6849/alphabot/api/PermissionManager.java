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

import com.google.common.io.Files;
import com.google.gson.*;
import org.pircbotx.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PermissionManager {
    private final List<Group> groups = new LinkedList<>();

    public void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            groups.clear();
            String json = Files.toString(new File("permissions.json"), Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
            JsonElement jelement = new JsonParser().parse(json);
            JsonObject output = jelement.getAsJsonObject();
            JsonObject group = output.get("groups").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> set = group.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Map.Entry en = (Map.Entry) it.next();
                JsonObject obj = new JsonParser().parse(en.getValue().toString()).getAsJsonObject();
                String name = obj.get("name").toString().replaceAll("\"", "");
                List<Permission> permissions = new ArrayList<Permission>();
                List<String> inheiritance = new ArrayList<String>();
                boolean exec = obj.get("exec").getAsBoolean();
                for(JsonElement perm : obj.getAsJsonArray("permissions")){
                    permissions.add(new Permission(perm.getAsString(), false));
                }
                for(JsonElement inheirit : obj.getAsJsonArray("inheritance")){
                    inheiritance.add(inheirit.getAsString());
                }
                groups.add(new Group(name, permissions, exec));
                for (Group g : groups) {
                    JsonObject gr = output.get("groups").getAsJsonObject().get(g.getName()).getAsJsonObject();
                    if (gr.has("inheritance")) {
                        JsonArray inherit = gr.get("inheritance").getAsJsonArray();
                        List<String> inheritance = new ArrayList<String>();
                        for (int i = 0; i < inherit.size(); i++) {
                            inheritance.add(inherit.get(i).getAsString());
                        }
                        for (String in : inheritance) {
                            Group ing = getGroupByName(in);
                            g.addInherit(ing);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PermissionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets a group by name, not case sensitive.
     *
     * @param id the name of the group to get, ie. "admin"
     * @return the group object if we find it, otherwise null.
     */
    public Group getGroupByName(String id) {
        for (Group group : groups) {

            if (group.getName().equalsIgnoreCase(id)) {
                return group;
            }
        }
        System.out.println("Groups:");
        for(Group g : groups){
            System.out.println(" - " + g.getName());
        }
        System.out.println("Invalid group " + id + " requested!");
        return null;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public PermissionManager getPermissionsManager() {
        return this;
    }

    /**
     * Returns the group a user belongs to, this ignores default
     * If a user belongs to two groups, then it will return the first it finds.
     *
     * @param user the User object to get the group of
     * @return the Group the user belongs to
     */
    public Group getUserGroup(User user) {
        //first check if any of the groups contain the user
        for (Group g : getGroups()) {
            if (!g.getName().equalsIgnoreCase("default")) {
                for (User u : g.getUsers().keySet()) {
                    if (u == user) {
                        System.out.println("0 Found group " + g.getName() + " for user " + user.getNick());
                        return g;
                    }
                }
            }
        }
        //if they haven't, parse all the json
        String json = null;
        try {
            json = Files.toString(new File("permissions.json"), Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
            JsonElement jelement = new JsonParser().parse(json);
            JsonObject output = jelement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> users = output.get("users").getAsJsonObject().entrySet();
            boolean hostmatch = false;
            Iterator it = users.iterator();
            int count = 0;
            while (it.hasNext()) {
                count++;
                Object next = it.next();
                Map.Entry en = (Map.Entry) next;
                String mask = en.getKey().toString().replaceAll("\"", "");
                String rank = en.getValue().toString().replaceAll("\"", "");
                Pattern p = Pattern.compile(mask.split("\\@")[0].replaceAll("\\*", ".*"));
                Matcher m = p.matcher(user.getNick());
                if (m.find()) {
                    p = Pattern.compile(mask.split("\\@")[1].replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*"));
                    m = p.matcher(user.getHostmask());
                    if (m.find()) {
                        Group target = getGroupByName(rank);
                        target.addUser(user, mask);
                        return target;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getGroupByName("Default");
    }

    public void save() {
        System.out.println("Saving....");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        String json = null;
        try {
            json = Files.toString(new File("permissions.json"), Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
            JsonElement jelement = parser.parse(json);
            JsonObject output = jelement.getAsJsonObject();
            HashMap<String, String> masks = new HashMap<String, String>();
            for (Group g : getGroups()) {
                if (!g.getName().equalsIgnoreCase("default")) {
                    for (User u : g.getUsers().keySet()) {
                        if (!masks.containsKey(g.getUsers().get(u))) {
                            masks.put(g.getUsers().get(u), g.getName());
                        }
                    }
                }
            }
            JsonElement obj = output.get("users");
            Set<Map.Entry<String, JsonElement>> users = output.get("users").getAsJsonObject().entrySet();
            Iterator it = users.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                masks.put(entry.getKey().toString().replaceAll("\"", ""), entry.getValue().toString().replaceAll("\"", ""));
            }
            for (String s : masks.keySet()) {
                obj.getAsJsonObject().remove(s);
                obj.getAsJsonObject().addProperty(s, masks.get(s));
            }
            JsonObject gr = output.get("groups").getAsJsonObject();
            for (Group g : groups) {
                gr.remove(g.getName());
                System.out.println(g.getName() + ":");
                //temporary group object to store the "new" group object
                //loop through all permissions in the actual group, and if the permission is inherited, then remove it.
                List<String> permissions = new ArrayList<String>();
                Iterator<Permission> iter = g.getPermissions().iterator();
                while(iter.hasNext()){
                    Permission perm = iter.next();
                    System.out.println("  - " + perm.getPermission() + ":" + perm.isInheirited());
                    if(!perm.isInheirited()){
                        permissions.add(perm.getPermission());
                    }
                }
                JsonObject group = parser.parse(gson.toJson(g)).getAsJsonObject();
                group.remove("permissions");
                group.add("permissions", parser.parse(gson.toJson(permissions)));
                group.remove("inheritance");
                group.add("inheritance", parser.parse(gson.toJson(g.getInheritance())));
                gr.add(g.getName(), group);
            }
            output.remove("groups");
            output.add("groups", gr);
            output.remove("users");
            output.add("users", obj);
            System.out.println(gson.toJson(output));
            Files.write(gson.toJson(output), new File("permissions.json"), Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Just a convenience method, the exact same as calling save() and load()
     */
    public void reload() {
        save();
        load();
    }
}
