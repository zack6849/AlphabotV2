/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.listeners;

import com.zack6849.alphabot.api.*;
import org.pircbotx.hooks.ListenerAdapter;

/**
 * @author Zack
 */
public class MessageEvent extends ListenerAdapter {

    private Config config;
    private PermissionManager manager;

    public MessageEvent(Config conf, PermissionManager man) {
        this.config = conf;
        this.manager = man;
    }

    @Override
    public void onMessage(org.pircbotx.hooks.events.MessageEvent event) {
        String trigger = config.getTrigger();
        if (event.getMessage().startsWith(trigger)) {
            try {
                String classname = Character.toUpperCase(event.getMessage().split(" ")[0].charAt(1)) + event.getMessage().split(" ")[0].substring(2).toLowerCase();
                String permission = "command." + classname.toLowerCase();
                if (manager.hasPermission(permission, event.getUser())) {
                    Command command = CommandRegistry.getCommand(classname);
                    command.setConfig(config);
                    command.execute(event);

                } else {
                    event.getBot().sendNotice(event.getUser(), config.getPermissionDenied().replaceAll("%USERNAME%", event.getUser().getNick()));
                }

            } catch (Exception e) {
                /*
                 * Unknown command >implying i give a fuck
                 */
                //Logger.getLogger(MessageEvent.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        for (String word : event.getMessage().split(" ")) {
            if (Utils.isUrl(word)) {
                event.getBot().sendMessage(event.getChannel(), event.getUser().getNick() + "'s URL: " + Utils.getTitle(word));
            }
        }
    }
}
