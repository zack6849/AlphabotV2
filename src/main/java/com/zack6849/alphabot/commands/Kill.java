/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Zack
 */
public class Kill extends Command
{

    private Config config;

    public Kill()
    {
        super("Kill", "command.kill");
    }

    @Override
    public void execute(MessageEvent event)
    {
        event.getBot().shutdown(true);
    }

    @Override
    public void setConfig(Config config)
    {
        this.config = config;
    }
}
