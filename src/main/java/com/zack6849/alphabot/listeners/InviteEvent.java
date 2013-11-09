package com.zack6849.alphabot.listeners;

import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.ListenerAdapter;

public class InviteEvent extends ListenerAdapter {

    private Config config;
    private PermissionManager manager;

    public InviteEvent(Config conf, PermissionManager man) {
        this.config = conf;
        this.manager = man;
    }

    @Override
    public void onInvite(org.pircbotx.hooks.events.InviteEvent event) {
        if (config.isAutoAcceptInvite()) {
            event.getBot().sendIRC().joinChannel(event.getChannel());
        }
    }
}