/*
 *    This file is part of Alphabot.
 *
 *    Alphabot is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.BotConfiguration;
import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.PermissionManager;
import com.zack6849.alphabot.api.Utils;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class Ban extends Command {

    private BotConfiguration config;
    private PermissionManager manager;

    public Ban() {
        super("Ban", "Bans a user from a channel", "Ban <hostmask> (optional: reason) | Ban <#channel> <hostmask> (optional: reason)");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        User sender = event.getUser();
        if (args.length == 3) {
            Channel chan = event.getBot().getUserChannelDao().getChannel(args[1]);
            User target = event.getBot().getUserChannelDao().getUser(args[2]);
            int senderrank = Utils.getRank(chan, sender);
            int targetrank = Utils.getRank(chan, target);
            if (senderrank > targetrank) {
                chan.send().ban("*!*@" + target.getHostmask());
                chan.send().kick(target);
                return true;
            } else {
                sender.send().message("You cant kick someone with a higher rank than you!");
                return false;
            }
        }
        if (args.length == 2) {
            Channel chan = event.getChannel();
            int senderrank = Utils.getRank(event.getChannel(), event.getUser());
            User target = event.getBot().getUserChannelDao().getUser(args[1]);
            int targetrank = Utils.getRank(event.getChannel(), target);
            if (senderrank > targetrank) {
                chan.send().ban("*!*@" + target.getHostmask());
                chan.send().kick(target, "Ban requested by " + sender.getNick());
                return true;
            } else {
                sender.send().message("You cant kick someone with a higher rank than you!");
                return false;
            }
        }
        if (args.length > 3) {
            if (args[1].startsWith("#")) {
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String reason = sb.toString().trim();
                Channel chan = event.getBot().getUserChannelDao().getChannel(args[1]);
                User target = event.getBot().getUserChannelDao().getUser(args[2]);
                chan.send().ban("*!*@" + target.getHostmask());
                chan.send().kick(target, reason);
                return true;
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                Channel chan = event.getChannel();
                User target = event.getBot().getUserChannelDao().getUser(args[1]);
                chan.send().ban("*!*@" + target.getHostmask());
                chan.send().kick(target, sb.toString().trim());
                return true;
            }
        }
        return false;
    }

    @Override
    public void setConfig(BotConfiguration config) {
        this.config = config;
    }

    @Override
    public void setManager(PermissionManager manager) {
        this.manager = manager;
    }
}
