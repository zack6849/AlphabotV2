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
import com.zack6849.alphabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;

public class Google extends Command {
    public Google() {
        super("Google", "Searches google for something.", "Google how to google");
    }

    private Config config;
    private PermissionManager manager;

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length >= 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String google = sb.toString().trim();
            event.getChannel().send().message(Utils.google(google));
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