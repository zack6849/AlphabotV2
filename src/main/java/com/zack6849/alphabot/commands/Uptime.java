/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.Main;
import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * @author Zack
 */
public class Uptime extends Command {

    private Config config;
    private PermissionManager manager;

    public Uptime() {
        super("Uptime");
    }

    @Override
    public void execute(MessageEvent event) {
        Long time = System.currentTimeMillis() - Main.startup;
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) (time / (60000)) % 60;
        int hours = (int) (time / (3600000)) % 24;
        int days = (int) (time / 86400000);
        String uptime = String.format("%d Days %d Hours %d Minutes and %d seconds", days, hours, minutes, seconds);
        event.getBot().sendMessage(event.getChannel(), "Current bot uptime: " + uptime);
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void setManager(PermissionManager manager) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
