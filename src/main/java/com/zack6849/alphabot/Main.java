package com.zack6849.alphabot;

import com.zack6849.alphabot.api.CommandRegistry;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import com.zack6849.alphabot.commands.Kill;
import com.zack6849.alphabot.commands.Test;
import com.zack6849.alphabot.commands.Uptime;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static long startup = 0;
    public static void main( String[] args )
    {
        try
        {
            startup = System.currentTimeMillis();
            final PircBotX bot = new PircBotX();
            Config config = new Config();
            CommandRegistry.register(new Test());
            CommandRegistry.register(new Uptime());
            CommandRegistry.register(new Kill());
            PermissionManager manager = new PermissionManager(config);
            config.load();
            manager.load();
            bot.setName(config.getBotNickname());
            bot.setLogin(config.getBotIdent());
            bot.getListenerManager().addListener(new com.zack6849.alphabot.listeners.MessageEvent(config, manager));
            bot.getListenerManager().addListener(new com.zack6849.alphabot.listeners.InviteEvent(config, manager));
            bot.setVerbose(config.isDebug());
            bot.setEncoding(Charset.forName("UTF-8"));
            bot.setAutoReconnectChannels(config.isAutoRejoinChannel());
            bot.setAutoReconnect(config.isAutoReconnectServer());
            bot.setAutoNickChange(config.isAutoNickChange());
            bot.setVersion(config.getCtcpVersion());
            bot.setFinger(config.getCtcpFinger());
            bot.setEncoding(Charset.forName("UTF-8"));
            bot.connect(config.getServerHostame(), Integer.parseInt(config.getServerPort()), config.getServerPassword());
            bot.sendMessage("NickServ", "identify " + config.getBotUsername() + " " + config.getBotPassword());
            for(String channel : config.getChannels()){
                bot.joinChannel(channel);
            }
            new Thread(new Runnable() {

                @Override
                public void run()
                {
                    Scanner in = new Scanner(System.in);
                    String line = "";
                    while((line = in.nextLine())!= null){
                        bot.sendRawLineNow(line);
                    }
                }
            }).start();
        } catch (IOException | IrcException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
