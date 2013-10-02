package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;

public class Part extends Command {
    public Part() {
        super("Part", "Removes the bot from a channel", "part #channel");
    }

    private Config config;
    private PermissionManager manager;

    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 2) {
            Channel target = event.getBot().getChannel(args[1]);
            if (event.getBot().getChannels().contains(target)) {
                event.getBot().partChannel(target);
            } else {
                event.getBot().sendNotice(event.getUser(), "I'm not in the channel " + args[1] + "!");
            }
        } else {
            event.getBot().partChannel(event.getChannel());
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
