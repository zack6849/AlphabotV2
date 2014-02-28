package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.WhoisEvent;


public class Test extends Command {

    private Config config;
    private PermissionManager manager;

    public Test() {
        super("Test", "This is a test command", "Test!");
    }

    @Override
    public boolean execute(MessageEvent event) {
        event.getChannel().send().message("Test!");
        event.getChannel().send().message(event.getUser().getUserLevels(event.getChannel()).toString());
        WhoisEvent.Builder<PircBotX> whois = new WhoisEvent.Builder();
        whois.setNick(event.getUser().getNick());
        WhoisEvent who = whois.generateEvent(event.getBot());
        event.getChannel().send().message("Logged in as: " + who.getRegisteredAs());

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
