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

package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.*;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


public class Manager extends Command {
    private BotConfiguration config;
    private PermissionManager manager;

    public Manager() {
        super("Manager", "Manage user permissions and groups", "Manager <addu|addp|addi|delp|delu|deli>");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length >= 1) {
            if (args[1].toLowerCase().equals("addu")) {
                if (args.length == 4) {
                    User u = event.getBot().getUserChannelDao().getUser(args[2]);
                    Group g = manager.getGroupByName(args[3]);
                    if (manager.getUserGroup(event.getUser()).getInheritance().contains(g.getName())) {
                        if (u != null) {
                            g.addUser(u, "*@" + u.getHostmask());
                            event.getUser().send().notice("Granted access to group  " + g.getName() + " to user " + u.getNick());
                        } else {
                            event.getUser().send().notice("Couldn't find the user " + args[2]);
                            return true;
                        }
                    } else {
                        event.getUser().send().notice(Colors.RED + "You can't give someone a group higher than you!");
                    }
                } else {
                    event.getUser().send().notice("Syntax: manager addu <user> <group>");
                }
            }

            if (args[1].toLowerCase().equals("addp")) {
                if (args.length == 4) {
                    Permission permission = new Permission(args[2], false);
                    Group group = manager.getGroupByName(args[3]);
                    if (group != null) {
                        if (manager.getUserGroup(event.getUser()).hasPermission(permission)) {
                            group.addPermission(permission);
                            event.getUser().send().notice("Added permission " + permission.getPermission() + " to group  " + group.getName());
                        } else {
                            event.getUser().send().notice(Colors.RED + "You can't give someone a permission you don't have!");
                        }
                    } else {
                        event.getUser().send().notice("Couldn't find the group " + args[3]);
                        return true;
                    }
                } else {
                    event.getUser().send().notice("Syntax: manager addp <permission> <group> ");
                }
            }

            if (args[1].toLowerCase().contains("delp")) {
                if (args.length == 4) {
                    Permission permission = new Permission(args[2], false);
                    Group group = manager.getGroupByName(args[3]);
                    Group usergroup = manager.getUserGroup(event.getUser());
                    if (group != null) {
                        if (usergroup.hasPermission(permission) && usergroup.getInheritance().contains(group.getName()) || usergroup == group) {
                            group.removePermission(permission.getPermission());
                        } else {
                            event.getUser().send().notice(Colors.RED + "You can't remove permissions from a group higher than you!");
                        }
                    } else {
                        event.getUser().send().notice("Couldn't find the group " + args[3]);
                        return true;
                    }
                } else {
                    event.getUser().send().notice("Syntax: manager delp <permission> <group> ");
                }
            }

            if (args[1].toLowerCase().contains("delu")) {

                //delete user args[3] from group args[2]
                if (args.length == 4) {
                    User u = event.getBot().getUserChannelDao().getUser(args[2]);
                    Group g = manager.getGroupByName(args[3]);
                    if (manager.getUserGroup(event.getUser()).getInheritance().contains(g.getName())) {
                        if (u != null) {
                            g.getUsers().remove(u);
                            event.getUser().send().notice("Removed access to group " + g.getName() + " from " + u.getNick());
                        } else {
                            event.getUser().send().notice("Couldn't find the user " + args[2] + " are they online?");
                            return true;
                        }
                    } else {
                        event.getUser().send().message(Colors.RED + "You can't give someone a group higher than you!");
                    }
                } else {
                    event.getUser().send().notice("Syntax: manager delu <user> <group>");
                }
            }
        }
        return true;
    }

    @Override
    public void setConfig(BotConfiguration config) {
        this.config = config;
    }

    @Override
    public void setManager(PermissionManager manager) {
        this.manager = manager;
    }
}
