package com.zack6849.alphabot;

import com.zack6849.alphabot.api.Config;
import java.io.IOException;
import java.nio.charset.Charset;
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
    public static void main( String[] args )
    {
        try
        {
            PircBotX bot = new PircBotX();
            Config config = new Config();
            config.load();
            bot.setName(config.getBotNickname());
            bot.setLogin(config.getBotIdent());
            bot.getListenerManager().addListener(new com.zack6849.alphabot.listeners.MessageEvent(config));
            bot.getListenerManager().addListener(new com.zack6849.alphabot.listeners.InviteEvent(config));
            bot.setVerbose(config.isDebug());
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
        } catch (IOException | IrcException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
