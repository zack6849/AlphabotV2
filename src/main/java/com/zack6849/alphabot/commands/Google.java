package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import com.zack6849.alphabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;

public class Google extends Command {
    public Google() {
        super("Google", "Searches google for something.", "Google how to google");
    }

    private Config config;
    private PermissionManager manager;

    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length >= 3) {
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String google = sb.toString().trim();
            event.getBot().sendMessage(event.getChannel(), google);
            event.getBot().sendMessage(event.getChannel(), Utils.google(google));
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
