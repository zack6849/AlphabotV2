package com.zack6849.alphabot.commands;

import com.zack6849.alphabot.api.Command;
import com.zack6849.alphabot.api.Config;
import com.zack6849.alphabot.api.PermissionManager;
import org.pircbotx.hooks.events.MessageEvent;

public class Say extends Command {
    private PermissionManager manager;
    private Config config;

    public Say(){
        super("Say", "Send a message to the current channel");
    }
    @Override
    public boolean execute(MessageEvent event) {
      String[] args = event.getMessage().split(" ");
      StringBuilder builder = new StringBuilder();
      for(int i = 1; i < args.length; i++){
          builder.append(args[i]).append(" ");
      }
      event.getChannel().send().message(builder.toString().trim());
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
