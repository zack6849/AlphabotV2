package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;

public class Cycle extends Command{
    private static Config config;
    private static PermissionManager manager;

    public Cycle(){
        super("Cycle", "tells the bot to part and re-join the channel","part #channel or part");
    }

    @Override
    public boolean execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        if(args.length == 1){
            String name = event.getChannel().getName();
            event.getChannel().send().part();
            event.getBot().sendIRC().joinChannel(name);
            return true;
        }
        if(args.length == 2){
            Channel target = event.getBot().getUserChannelDao().getChannel(args[1]);
            String name = target.getName();
            target.send().part();
            event.getBot().sendIRC().joinChannel(name);
            return true;
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
