package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Unban extends Command {

    private Config config;
    private PermissionManager manager;

    public Unban() {
        super("Unban", "Removes a ban on a hostmask", "Unban hostmask or Unban #channel hostmask");
    }

    //huehuehue le copypasta faec
    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        User sender = event.getUser();
        //unban #channel username
        if (args.length == 3) {
            Channel target = event.getBot().getUserChannelDao().getChannel(args[1]);
            target.send().ban(args[2]);
        }
        //unaban username
        if (args.length == 2) {
            event.getChannel().send().ban(args[1]);
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
