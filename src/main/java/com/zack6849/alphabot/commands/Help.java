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

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.CommandRegistry;
import com.zack6849.alphabot.api.BotConfiguration;
import com.zack6849.alphabot.api.PermissionManager;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class Help extends Command {

    private PermissionManager manager;
    private BotConfiguration config;

    public Help() {
        super("Help", "List command names and how to use them.", "help or help <command>");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 1) {
            String header = String.format("| %s| %s| %s|", StringUtils.rightPad("Command Name", 15), StringUtils.rightPad("Description", 50), StringUtils.rightPad("Help", 65));
            String seperator = "+" + StringUtils.repeat('-', 16) + "+" + StringUtils.repeat('-', 51) + "+" + StringUtils.repeat('-', 66) + "+";
            event.getUser().send().notice(seperator);
            event.getUser().send().notice(header);
            event.getUser().send().notice(seperator);
            for (String s : CommandRegistry.commands.keySet()) {
                Command command = CommandRegistry.getCommand(s);
                event.getUser().send().notice(String.format("| %s| %s| %s|", StringUtils.rightPad(command.getName(), 15), StringUtils.rightPad(command.getDescription(), 50), StringUtils.rightPad(command.getHelp(), 65)));
            }
            event.getUser().send().notice(seperator);
            return true;
        }
        if (args.length == 2) {
            Command command = CommandRegistry.getCommand(StringUtils.capitalize(args[1].toLowerCase()));
            if (command != null) {
                event.getUser().send().notice(String.format("Help for command: %s - %s - %s", command.getName(), command.getDescription(), command.getHelp()));
            } else {
                event.getUser().send().notice("Could not find the command " + args[1] + ", are you sure you spelled it right?");
            }
            return true;
        }
        return false;
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
