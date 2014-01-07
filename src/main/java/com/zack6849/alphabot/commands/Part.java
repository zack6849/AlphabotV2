/*
 *  This file is part of Alphabot.
 *
 *  Alphabot is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Alphabot is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Alphabot.  If not, see <http://www.gnu.org/licenses/>.
 */

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
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 2) {
            Channel target = event.getBot().getUserChannelDao().getChannel(args[1]);
            event.getBot().getUserChannelDao().getAllChannels();
            if (event.getBot().getUserChannelDao().getAllChannels().contains(target)) {
                target.send().part();
                return true;
            } else {
                event.getUser().send().notice("I'm not in the channel " + args[1] + "!");
                return true;
            }
        } else {
            event.getChannel().send().part();
            return true;
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
