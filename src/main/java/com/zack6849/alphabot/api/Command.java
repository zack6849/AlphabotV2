package com.zack6849.alphabot.api;

import org.pircbotx.hooks.events.MessageEvent;

public abstract class Command {
    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(MessageEvent event);

    public abstract void setConfig(Config config);

    public abstract void setManager(PermissionManager manager);

}
