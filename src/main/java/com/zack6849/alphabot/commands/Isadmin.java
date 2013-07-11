/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Zack
 */
public class Isadmin extends Command
{

    private Config config;
    
    @Override
    public void setConfig(Config conf)
    {
        this.config = conf;
    }
    @Override
    public void run(MessageEvent event)
    {
        PircBotX bot = event.getBot();
        if(config.isAdmin(event.getUser().getNick(), event.getUser().getHostmask())){
            bot.sendMessage(event.getChannel(), "You're an admin!");
        }else{
            bot.sendMessage(event.getChannel(), "I sry, no admin 4 u.");
        }
    }
    @Override
    public String getDescription()
    {
        return "Disconnects the bot from the current server";
    }

    @Override
    public String getSyntax()
    {
        return config.getTrigger() + "Kill";
    }

    @Override
    public String getName()
    {
        return "Kill";
    }

    @Override
    public boolean isSecret()
    {
        return false;
    }
    
}
