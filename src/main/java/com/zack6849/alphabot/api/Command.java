package com.zack6849.alphabot.api;

import org.pircbotx.hooks.events.MessageEvent;

public abstract class Command {
    private final String name;
    private final String description;
    private final String help;

    /**
     * This constructor isn't recommended as it leaves the entry for the command in the help command rather sparse
     *
     * @param name The name of the command
     * @see Command(String name, String description, String help)
     */
    public Command(String name) {
        this.name = name;
        this.description = "No description available for command " + name;
        this.help = "No help available for command " + name;
    }

    /**
     * The help for the command defaults to: No help available for command <command>
     *
     * @param name        The name of the command
     * @param description A brief description of the command
     * @see Command(String name, String description, String help)
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.help = "No help available for command " + name;
    }

    /**
     * @param name        The name of the command
     * @param description A brief description of the command
     * @param help        Help for the command, usually usage information.
     */
    public Command(String name, String description, String help) {
        this.name = name;
        this.description = description;
        this.help = help;
    }

    /**
     * Get the name of the command
     *
     * @return the command's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the command
     *
     * @return the command's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the syntax / help for the command
     *
     * @return the syntax / help for the command
     */
    public String getHelp() {
        return this.help;
    }

    public abstract void execute(MessageEvent event);

    public abstract void setConfig(Config config);

    public abstract void setManager(PermissionManager manager);

}
