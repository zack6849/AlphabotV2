package com.zack6849.alphabot.api;

import org.pircbotx.hooks.events.MessageEvent;

public abstract class Command
{
    private final String name;
    private final String permission;
    public Command(String name)
    {
        this(name, null);
    }

    public Command(String name, String permission)
    {
        this.name = name;
        this.permission = permission;
    }

    public String getName()
    {
        return name;
    }

    public String getPermission()
    {
        return permission;
    }

    public abstract void execute(MessageEvent event);
    public abstract void setConfig(Config config);
    
}
