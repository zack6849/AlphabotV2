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

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;

public class Cycle extends Command {
    private Config config;
    private PermissionManager manager;

    public Cycle() {
        super("Cycle", "tells the bot to part and re-join the channel", "part #channel or part");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 1) {
            String name = event.getChannel().getName();
            event.getChannel().send().part();
            event.getBot().sendIRC().joinChannel(name);
            return true;
        }
        if (args.length == 2) {
            Channel target = event.getBot().getUserChannelDao().getChannel(args[1]);
            String name = target.getName();
            target.send().part();
            event.getBot().sendIRC().joinChannel(name);
            return true;
        }
        return false;
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
