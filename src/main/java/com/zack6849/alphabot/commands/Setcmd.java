package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Zack
 * Date: 11/24/13
 * Time: 3:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Setcmd extends Command {
    private Config config;
    private PermissionManager manager;
    public Setcmd(){
        super("Setcmd", "Set a custom command", "setcmd <trigger> <output> ex $setcmd test this is a test!");
    }

    @Override
    public boolean execute(MessageEvent event) {

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
