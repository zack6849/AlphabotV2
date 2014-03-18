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
package com.zack6849.alphabot;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.CommandRegistry;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import com.zack6849.runnables.ChatSocketListener;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.reflections.Reflections;
import org.slf4j.impl.SimpleLogger;

import java.nio.charset.Charset;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static long startup = 0;
    public static void main(String[] args) {
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[HH:mm:ss]");
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
        System.out.println("Starting");
        try {
            startup = System.currentTimeMillis();
            final Config config = new Config();
            PermissionManager manager = new PermissionManager();
            System.out.println("Loading and registering commands");
            config.load();
            manager.load();
            Reflections reflections = new Reflections("com.zack6849.alphabot.commands");
            Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
            for (Class c : subTypes) {
                Command cmd = CommandRegistry.getCommand(c.getSimpleName());
                //System.out.println("Registered command " + cmd.getName() + " as key " + c.getSimpleName());
                CommandRegistry.register(cmd);
            }
            //i have no idea what this is, but IDEA wouldn't shut the fuck up about changing it.
            Configuration.Builder<PircBotX> builder = new Configuration.Builder<PircBotX>();
            builder.setName(config.getBotNickname());
            builder.setRealName(config.getBotUsername());
            builder.setLogin(config.getBotIdent());
            builder.setFinger(config.getCtcpFinger());
            builder.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
            builder.setNickservPassword(config.getBotPassword());
            builder.setVersion("Alphbot v2.0 BETA");
            builder.setServer(config.getServerHostame(), Integer.parseInt(config.getServerPort()), config.getServerPassword());
            builder.getListenerManager().addListener(new com.zack6849.alphabot.listeners.MessageEvent(config, manager));
            builder.getListenerManager().addListener(new com.zack6849.alphabot.listeners.InviteEvent(config, manager));
            for (String channel : config.getChannels()) {
                builder.addAutoJoinChannel(channel);
            }
            PircBotX bot = new PircBotX(builder.buildConfiguration());
            System.out.println("Starting bot...");
            if (config.isEnableChatSocket()) {
                new Thread(new ChatSocketListener(bot, config.getChatSocketPort())).start();
            }
            bot.startBot();
            System.out.println("Shutting down");

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
