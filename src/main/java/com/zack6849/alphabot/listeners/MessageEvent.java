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

package com.zack6849.alphabot.listeners;

import com.zack6849.alphabot.api.*;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageEvent extends ListenerAdapter {

    private Config config;
    private PermissionManager manager;

    public MessageEvent(Config conf, PermissionManager man) {
        this.config = conf;
        this.manager = man;
    }

    @Override
    public void onMessage(org.pircbotx.hooks.events.MessageEvent event) {
        String trigger = config.getTrigger();
        if (event.getMessage().startsWith(trigger)) {
            try {
                String commandname = event.getMessage().split(" ")[0].substring(1).toLowerCase();
                File commandfile = new File("commands/" + event.getChannel().getName() + "/" + commandname + ".cmd");
                if (commandfile.exists()) {
                    BufferedReader in = new BufferedReader(new FileReader(commandfile));
                    String tmp;
                    while ((tmp = in.readLine()) != null) {
                        event.getChannel().send().message(commandname + ": " + tmp);
                    }
                    in.close();
                }
                String classname = Character.toUpperCase(event.getMessage().split(" ")[0].charAt(1)) + event.getMessage().split(" ")[0].substring(2).toLowerCase();
                String permission = "command." + classname.toLowerCase();
                if (manager.getUserGroup(event.getUser()).hasPermission(new Permission(permission, false))) {
                    Command command = CommandRegistry.getCommand(classname);
                    command.setConfig(config);
                    command.setManager(manager);
                    if (!command.execute(event)) {
                        event.getChannel().send().message(Colors.RED + "An error occurred! " + command.getHelp());
                        return;
                    }
                } else {
                    event.getUser().send().notice(config.getPermissionDenied().replaceAll("%USERNAME%", event.getUser().getNick()));
                }
            } catch (Exception e) {
                /*
                 * Unknown command
                 * >implying i give a fuck
                 * */
                Logger.getLogger(MessageEvent.class.getName()).log(Level.SEVERE, null, e);

            }
        }
        for (String word : event.getMessage().split(" ")) {
            if (Utils.isUrl(word)) {
                event.getChannel().send().message(Utils.munge(event.getUser().getNick()) + "'s URL: " + Utils.getTitle(word));
            }
        }
    }
}
