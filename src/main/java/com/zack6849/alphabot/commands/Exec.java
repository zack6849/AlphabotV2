package com.zack6849.alphabot.commands;

import bsh.EvalError;
import com.zack6849.alphabot.api.*;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Exec extends Command {

    private static Config config;
    private static PermissionManager manager;
    private static bsh.Interpreter interpreter;

    static {
        try {
            interpreter = new bsh.Interpreter();
            interpreter.getNameSpace().doSuperImport();
            interpreter.set("utils", new Utils());
            interpreter.set("conf", config);
            interpreter.set("registry", new CommandRegistry());
            interpreter.set("manager", manager);
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                interpreter.eval("java.lang.String getStuff(java.lang.String command){ java.lang.String output = \"\";java.lang.Process p = Runtime.getRuntime().exec(new java.lang.String[] {\"/bin/sh\", \"-c\", command}); BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));java.lang.String temp = \"\";while((temp = in.readLine()) != null){ output += temp + \"\\t\"; } return output; }");
            } else {
                //interpreter.eval("java.lang.String getStuff(java.lang.String command){ java.lang.String output = \"\";java.lang.Process p = Runtime.getRuntime().exec(command}); BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));java.lang.String temp = \"\";while((temp = in.readLine()) != null){ output += temp + \"\\t\"; } return output; }");
            }
        } catch (Exception ex) {
            Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Exec() {
        super("Exec", "Execute java code at runtime", "Exec <code> ex. exec bot.sendMessage(chan, \"Hello world!\");");
    }

    @Override
    public void execute(MessageEvent event) {
        String[] args = event.getMessage().split(" ");
        StringBuilder sb = new StringBuilder();
        if (args.length >= 2) {
            try {
                interpreter.set("event", event);
                interpreter.set("bot", event.getBot());
                interpreter.set("chan", event.getChannel());
                interpreter.set("user", event.getUser());
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String command = sb.toString().trim();
                interpreter.eval(command);
            } catch (EvalError ex) {
                Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
                event.getBot().sendMessage(event.getChannel(), ex.toString());
            }

        }
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
