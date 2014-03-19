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
import org.pircbotx.hooks.events.MessageEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Setcmd extends Command {
    private Config config;
    private PermissionManager manager;

    public Setcmd() {
        super("Setcmd", "Set a custom command", "setcmd <trigger> <output> ex $setcmd test this is a test!");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length > 1) {
            String commandname = args[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            try {
                File command = new File("commands/" + event.getChannel().getName() + "/" + commandname + ".cmd");
                command.getParentFile().mkdirs();
                command.createNewFile();
                PrintWriter writer = new PrintWriter(new FileWriter(command));
                for (String s : sb.toString().trim().split("\\\\n")) {
                    writer.println(s);
                }
                writer.flush();
                writer.close();
                return true;
            } catch (Exception e) {
                event.getChannel().send().message("An error occured!");
            }
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
