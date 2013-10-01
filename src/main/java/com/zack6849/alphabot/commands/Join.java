package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;

public class Join extends Command {

    private Config config;
    private PermissionManager manager;

    public Join() {
        super("Join");
    }

    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 2) {
            Channel target = event.getBot().getChannel(args[1]);
            event.getBot().sendRawLineNow("KNOCK " + target.getName() + " :Asked to join by " + event.getUser().getNick());
            event.getBot().joinChannel(target.getName());
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
