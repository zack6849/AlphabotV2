package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import com.zack6849.alphabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;


public class Mcstatus extends Command {
    private Config config;
    private PermissionManager manager;

    public Mcstatus() {
        super("Mcstatus", "Shows the status of various minecraft servers", "if you need help with this command, i will be dissapointed.");
    }

    @Override
    public boolean execute(MessageEvent event) {
        event.getChannel().send().message(Utils.checkMojangServers());
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
