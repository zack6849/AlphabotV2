/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Zack
 */
public class Test extends Command
{
    public Test(){
        super("Test", "command.test");
    }

    @Override
    public void execute(MessageEvent event)
    {
        event.getBot().sendMessage(event.getChannel(), "Test!");
    }
    
}
