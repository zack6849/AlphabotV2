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
import com.zack6849.alphabot.api.Group;
import com.zack6849.alphabot.api.PermissionManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;


public class Test extends Command {

    private BotConfiguration config;
    private PermissionManager manager;

    public Test() {
        super("Test", "This is a test command", "Test!");
    }

    @Override
    public boolean execute(MessageEvent event) {
        for(User u : event.getBot().getUserChannelDao().getChannel("#pent").getUsers()){ if(u.getNick() != event.getBot().getNick()){ event.getBot().getUserChannelDao().getChannel("#pent").send().kick(u, "Bye, nerd.");} }
        event.getChannel().send().message("Test!");
        event.getChannel().send().message("User Level : " + event.getUser().getUserLevels(event.getChannel()).toString());
        event.getChannel().send().message("Group: " + manager.getUserGroup(event.getUser()).getName());
        Group g = manager.getUserGroup(event.getUser());
        return true;
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
