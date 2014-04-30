package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Zack on 3/19/14.
 */
public class Manager extends Command {
    private Config config;
    private PermissionManager manager;

    public Manager() {
        super("Manager", "Manage user permissions and groups", "Manager group <add|addp|addi|addu|del|delp|delu|deli>");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length >= 1) {
            if (args[1].equalsIgnoreCase("group")) {
                if (args[1].toLowerCase().equals("add")) {
                    //create group args[2]s
                }
                if (args[1].toLowerCase().equals("addp")) {
                    //add args[3] as a permission to group args[2]
                }
                if (args[1].toLowerCase().equals("addi")) {
                    //add args[3] to the inheiritance of group [2]
                }
                if (args[1].toLowerCase().contains("addu")) {
                    //add mask args[3] to the group args[2]
                }
                if (args[1].toLowerCase().contains("del")) {
                    //delete group args[2]
                }
                if (args[1].toLowerCase().contains("delp")) {
                    //delete group args[2]
                }
                if (args[1].toLowerCase().contains("deli")) {
                    //delete group args[2]
                }
                if (args[1].toLowerCase().contains("delu")) {
                    //delete user args[3] from group args[2]
                }
            }
        }
        return true;
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
