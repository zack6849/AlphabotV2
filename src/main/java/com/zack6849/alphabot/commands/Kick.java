/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Zack
 */
public class Kick extends Command {

    private Config config;
    private PermissionManager manager;

    public Kick() {
        super("Kick");
    }

    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        User sender = event.getUser();
        if (args.length == 3) {
            Channel chan = event.getBot().getChannel(args[1]);
            User target = event.getBot().getUser(args[2]);
            if (chan.isOp(sender)) {
                event.getBot().kick(chan, target);
            }
            if (chan.hasVoice(sender)) {
                if (!chan.hasVoice(target) && !chan.isOp(target)) {
                    event.getBot().kick(chan, target);
                } else {
                    event.getBot().sendNotice(sender, config.getPermissionDenied());
                }
            }
        }
        if (args.length == 2) {
            Channel chan = event.getChannel();
            User target = event.getBot().getUser(args[1]);
            if (chan.isOp(sender)) {
                event.getBot().kick(chan, target);
            }
            if (chan.hasVoice(sender)) {
                if (!chan.hasVoice(target) && !chan.isOp(target)) {
                    event.getBot().kick(chan, target);
                } else {
                    event.getBot().sendNotice(sender, config.getPermissionDenied());
                }
            }
        }
        if (args.length > 3) {
            //!kick #channel username reason
            if (args[1].startsWith("#")) {
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String reason = sb.toString().trim();
                Channel chan = event.getBot().getChannel(args[1]);
                User target = event.getBot().getUser(args[2]);
                if (chan.isOp(sender)) {
                    event.getBot().kick(chan, target, reason);
                }
                if (chan.hasVoice(sender)) {
                    if (!chan.hasVoice(target) && !chan.isOp(target)) {
                        event.getBot().kick(chan, target, reason);
                    } else {
                        event.getBot().sendNotice(sender, config.getPermissionDenied());
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                Channel chan = event.getChannel();
                User target = event.getBot().getUser(args[1]);
                if (chan.isOp(sender)) {
                    event.getBot().kick(chan, target, sb.toString().trim());
                }
                if (chan.hasVoice(sender)) {
                    if (!chan.hasVoice(target) && !chan.isOp(target)) {
                        event.getBot().kick(chan, target, sb.toString().trim());
                    } else {
                        event.getBot().sendNotice(sender, config.getPermissionDenied());
                    }
                }
            }
        }
    }

    public boolean isChannel(String name) {
        return name.startsWith("#");
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
