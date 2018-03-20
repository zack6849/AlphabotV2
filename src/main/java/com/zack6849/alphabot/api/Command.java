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


    /**
     * Execute the command
     *
     * @param event The MessageEvent to fire the command with
     */
    public abstract boolean execute(MessageEvent event);

    /**
     * Set the config object to be used by the command
     * Used to pass the config to the class.
     *
     * @param config the config object to pass
     */
    public abstract void setConfig(BotConfiguration config);

    /**
     * Set the PermissionManager object for the command
     * Used to pass the command a permissions manager
     *
     * @param manager the permissionsmanager instance to pass to the class
     */
    public abstract void setManager(PermissionManager manager);

}
