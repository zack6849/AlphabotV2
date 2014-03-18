package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Zack on 2/27/14.
 */
public class Reload extends Command {
    private PermissionManager manager;
    private Config config;

    public Reload() {
        super("Reload", "Reload the bot's configurations.");
    }

    @Override
    public boolean execute(MessageEvent event) {
        manager.reload();
        config.load();
        event.getChannel().send().message("Reload successful!");
        return true;
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
