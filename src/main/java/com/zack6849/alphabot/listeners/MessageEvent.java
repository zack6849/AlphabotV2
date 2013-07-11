/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.listeners;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.hooks.ListenerAdapter;

/**
 *
 * @author Zack
 */
public class MessageEvent extends ListenerAdapter
{

    private Config config;

    public MessageEvent(Config conf)
    {
        this.config = conf;
    }

    @Override
    public void onMessage(org.pircbotx.hooks.events.MessageEvent event)
    {
        String trigger = config.getTrigger();
        //event.getBot().sendMessage(event.getChannel(), "~" + event.getUser().getLogin() + "@" + event.getUser().getHostmask());
        if (event.getMessage().startsWith(trigger))
        {
            try
            {
                
                String classname = Character.toUpperCase(event.getMessage().split(" ")[0].charAt(1)) + event.getMessage().split(" ")[0].substring(2).toLowerCase();
                Command command = (Command) Command.getOrCreateNewInstance(classname);
                command.setConfig(config);
                command.run(event);
            } catch (Exception e)
            {
                Logger.getLogger(MessageEvent.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
