/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.Main;
import com.zack6849.alphabot.api.Command;
import java.util.concurrent.TimeUnit;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Zack
 */
public class Uptime extends Command
{

    public Uptime()
    {
        super("Uptime", "command.uptime");
    }

    @Override
    public void execute(MessageEvent event)
    {
        Long time = System.currentTimeMillis() - Main.startup;
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) (time / (60000)) % 60;
        int hours = (int) (time / (3600000)) % 24;
        int days = (int) (time / 86400000);
        String uptime = String.format("%d Days %d Hours %d Minutes and %d seconds", days, hours, minutes, seconds);
        event.getBot().sendMessage(event.getChannel(), "Current bot uptime: " + uptime);
    }
}
