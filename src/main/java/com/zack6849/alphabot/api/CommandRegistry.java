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

package com.zack6849.alphabot.api;

import java.util.HashMap;

public class CommandRegistry {

    public static HashMap<String, Command> commands = new HashMap<>();

    public static void register(Command command) {
        if (commands.containsKey(command.getName())) {
            return;
        }
        commands.put(command.getName().toLowerCase(), command);
    }

    public static void unRegister(Command command) {
        commands.remove(command.getName().toLowerCase());
    }

    public static Command getCommand(String name) {
        if (commands.containsKey(name)) {
            return commands.get(name);
        } else {
            try {
                commands.put(name, (Command) Command.class.getClassLoader().loadClass("com.zack6849.alphabot.commands." + name).newInstance());
                return commands.get(name);
            } catch (Exception ex) {
                //Logger.getLogger(CommandRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
