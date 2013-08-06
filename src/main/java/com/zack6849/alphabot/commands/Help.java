/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import java.util.Set;
import org.pircbotx.hooks.events.MessageEvent;
import org.reflections.Reflections;

/**
 *
 * @author Zack
 */
public class Help extends Command
{

    private Config config;
    private PermissionManager manager;
    @Override
    public void run(MessageEvent event)
    {
        String[] args = event.getMessage().split(" ");
        if (args.length == 1)
        {
            Reflections reflections = new Reflections("com.zack6849.alphabot.commands");
            Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
            for (Class c : subTypes)
            {
                Command cmd = (Command) Command.getOrCreateNewInstance(c.getSimpleName());
                if (!cmd.isSecret())
                {
                    event.getBot().sendNotice(event.getUser(), String.format("%s - \t%s ", cmd.getName().replaceAll("%CLASSNAME%", c.getSimpleName().replaceAll("\\.class", "")), cmd.getDescription().replaceAll("%CLASSNAME%", c.getSimpleName().replaceAll("\\.class", ""))));
                } else
                {
                    if (config.isAdmin(event.getUser().getNick(), event.getUser().getHostmask()))
                    {
                        event.getBot().sendNotice(event.getUser(), "TOP SEKRIT! " + String.format("%s - \t%s ", cmd.getName().replaceAll("%CLASSNAME%", c.getSimpleName().replaceAll("\\.class", "")), cmd.getDescription().replaceAll("%CLASSNAME%", c.getSimpleName().replaceAll("\\.class", ""))));
                    }
                }
            }
        }
        if (args.length == 2)
        {
            String classname = Character.toUpperCase(event.getMessage().split(" ")[1].charAt(0)) + event.getMessage().split(" ")[1].substring(1).toLowerCase();
            Command command = (Command) Command.getOrCreateNewInstance(classname);
            event.getBot().sendNotice(event.getUser(), "Help for command " + command.getName().replaceAll("%CLASSNAME%", classname));
            event.getBot().sendNotice(event.getUser(), "Description: " + command.getDescription().replaceAll("%CLASSNAME%",classname ));
            event.getBot().sendNotice(event.getUser(), "Usage: " + command.getSyntax().replaceAll("%CLASSNAME%", classname));
        }
    }

    @Override
    public void setConfig(Config conf)
    {
        this.config = conf;
    }
    @Override
    public void setManager(PermissionManager man){
        this.manager = man;
    }

    @Override
    public String getDescription()
    {
        return "Used to get help with commands.";
    }

    @Override
    public String getSyntax()
    {
        return "!help or !help <command>";

    }

    @Override
    public String getName()
    {
        return "Help";
    }

    @Override
    public boolean isSecret()
    {
        return false;
    }
}
