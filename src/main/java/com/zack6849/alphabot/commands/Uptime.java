package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.Main;
import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

public class Uptime extends Command {

    private Config config;
    private PermissionManager manager;

    public Uptime() {
        super("Uptime", "Gets the bot's uptime", "Uptime");
    }

    @Override
    public void execute(MessageEvent event) {
        Long time = System.currentTimeMillis() - Main.startup;
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) (time / (60000)) % 60;
        int hours = (int) (time / (3600000)) % 24;
        int days = (int) (time / 86400000);
        String uptime = String.format("%d Days %d Hours %d Minutes and %d seconds", days, hours, minutes, seconds);
        event.getChannel().send().message("Current bot uptime: " + uptime);
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
