package com.zack6849.alphabot.api;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandRegistry {

    public static HashMap<String, Command> commands = new HashMap<>();

    public static void register(Command command) {
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
                Logger.getLogger(CommandRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
