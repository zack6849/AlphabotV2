package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;

/**
 * Created by Zack on 1/7/14.
 */
public class Status extends Command {
    private Config config;
    private PermissionManager manager;

    public Status() {
        super("Status", "Shows the status of the bot");
    }

    @Override
    public boolean execute(org.pircbotx.hooks.events.MessageEvent event) {
        Runtime runtime = Runtime.getRuntime();
        long totalmemory = runtime.totalMemory();
        long usedmemory = runtime.freeMemory();
        String stats = String.format("%s/%s megabytes used.", (totalmemory - usedmemory) / 1048576, totalmemory / 1048576);
        event.getChannel().send().message(stats);
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
