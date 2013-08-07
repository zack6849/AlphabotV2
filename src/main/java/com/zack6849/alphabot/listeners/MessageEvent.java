/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.listeners;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.CommandRegistry;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import com.zack6849.alphabot.api.Utils;
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
    private PermissionManager manager;

    public MessageEvent(Config conf, PermissionManager man)
    {
        this.config = conf;
        this.manager = man;
    }

    @Override
    public void onMessage(org.pircbotx.hooks.events.MessageEvent event)
    {
        String trigger = config.getTrigger();
        if (event.getMessage().startsWith(trigger))
        {
            try
            {
                String classname = Character.toUpperCase(event.getMessage().split(" ")[0].charAt(1)) + event.getMessage().split(" ")[0].substring(2).toLowerCase();
                String permission = "command." + event.getMessage().split(" ")[0].toLowerCase().substring(1);
                if (manager.hasPermission(permission, event.getUser()))
                {
                    Command command = CommandRegistry.getCommand(classname);
                    System.out.println(command != null);
                    command.execute(event);

                } else
                {
                    event.getBot().sendNotice(event.getUser(), config.getPermissionDenied().replaceAll("%USERNAME%", event.getUser().getNick()));
                }

            } catch (Exception e)
            {
                Logger.getLogger(MessageEvent.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        for (String word : event.getMessage().split(" "))
        {
            if (Utils.isUrl(word))
            {
                event.getBot().sendMessage(event.getChannel(), event.getUser().getNick() + "'s URL: " + Utils.getTitle(word));
            }
        }
    }
}
