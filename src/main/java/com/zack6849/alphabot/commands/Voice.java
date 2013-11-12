package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

public class Voice extends Command {

    private Config config;
    private PermissionManager manager;

    public Voice() {
        super("Voice", "Give a user voice in a channel", "Voice <username>");
    }

    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 2) {
            event.getBot().voice(event.getChannel(), event.getBot().getUser(args[1]));
        }
        if (args.length == 3) {
            event.getBot().voice(event.getBot().getChannel(args[1]), event.getBot().getUser(args[2]));
        }
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void setManager(PermissionManager manager) {
        this.manager = manager;
    }
}
