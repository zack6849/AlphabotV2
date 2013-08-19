/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Zack
 */
public class Unban extends Command
{

    public Unban()
    {
        super("Unban", "command.unban");
    }
    private Config config;

    //huehuehue le copypasta faec
    @Override
    public void execute(MessageEvent event)
    {
        String[] args = event.getMessage().split(" ");
        User sender = event.getUser();
        if (args.length == 3)
        {
            Channel target = event.getBot().getChannel(args[1]);
            if(target.hasVoice(sender) || target.isOp(sender)){
                
            }
        }
        if (args.length == 2)
        {
            
        }
        if (args.length > 3)
        {
            if (args[1].startsWith("#"))
            {
                
            }
        }
    }

    @Override
    public void setConfig(Config config)
    {
        this.config = config;
    }
}
