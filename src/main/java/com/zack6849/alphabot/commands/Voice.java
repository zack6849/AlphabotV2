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
import com.zack6849.alphabot.api.BotConfiguration;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

public class Voice extends Command {

    private BotConfiguration config;
    private PermissionManager manager;

    public Voice() {
        super("Voice", "Give a user voice in a channel", "Voice <username>");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 2) {
            event.getChannel().send().voice(event.getBot().getUserChannelDao().getUser(args[1]));
            return true;
        }
        if (args.length == 3) {
            event.getBot().getUserChannelDao().getChannel(args[1]).send().voice(event.getBot().getUserChannelDao().getUser(args[2]));
            return true;
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
