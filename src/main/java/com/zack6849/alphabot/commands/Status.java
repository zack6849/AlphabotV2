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

import com.zack6849.alphabot.Main;
import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;

public class Status extends Command {
    private Config config;
    private PermissionManager manager;

    public Status() {
        super("Status", "Shows the status of the bot");
    }

    @Override
    public boolean execute(org.pircbotx.hooks.events.MessageEvent event) {
        Runtime runtime = Runtime.getRuntime();
        long totalmemory = runtime.totalMemory();
        long usedmemory = runtime.freeMemory();
        String stats = String.format("%s/%s megabytes used, with %s processors availible, and %s running threads", (totalmemory - usedmemory) / 1048576, totalmemory / 1048576, runtime.availableProcessors(), Thread.activeCount());
        Long time = System.currentTimeMillis() - Main.startup;
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) (time / (60000)) % 60;
        int hours = (int) (time / (3600000)) % 24;
        int days = (int) (time / 86400000);
        String uptime = String.format("%d Days %d Hours %d Minutes and %d seconds", days, hours, minutes, seconds);
        event.getChannel().send().message("Current bot uptime: " + uptime);
        event.getChannel().send().message("JVM Information: " + stats);
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
