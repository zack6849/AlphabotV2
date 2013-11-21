package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.CommandRegistry;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

public class Help extends Command {

    private PermissionManager manager;
    private Config config;

    public Help() {
        super("Help", "List command names and how to use them.", "help or help <command>");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 1) {
            for (String s : CommandRegistry.commands.keySet()) {
                Command command = CommandRegistry.getCommand(s);
                event.getUser().send().notice(String.format("%s - %s", command.getName(), command.getDescription()));
            }
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
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void setManager(PermissionManager manager) {
        this.manager = manager;
    }
}
