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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zack6849.alphabot.Main;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class BotConfiguration {
    private boolean debug;
    private boolean autoNickChange;
    private boolean autoReconnectServer;
    private boolean autoRejoinChannel;
    private boolean autoAcceptInvite;
    private boolean useSSL;
    private boolean verifySSL;
    private boolean enableChatSocket;
    private String trigger;
    private String serverHostame;
    private String serverPassword;
    private String serverPort;
    private String botNickname;
    private String botUsername;
    private String botIdent;
    private String botPassword;
    private String ctcpFinger;
    private String ctcpVersion;
    private String chatSocketPassword;
    private List<String> channels;
    private List<String> loggedChannels;
    private String permissionDenied;
    private Properties properties;
    private int chatSocketPort;

    public void load() {
        File cfg = new File(new File("").getAbsolutePath(), "config.json");
        if(!cfg.exists()){
            try {
                System.out.println("[!!] No configuration file found! generating a new one!");
                BufferedReader s = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/config.json")));
                Files.write(cfg.toPath(), s.lines().collect(Collectors.toList()));
                System.out.println("[!!] Done!");
            } catch (IOException e) {
                System.out.println("[!!] Failed to create config file: ");
                e.printStackTrace();
            }
        }

        System.setProperty("config.file", cfg.getAbsolutePath());
        Config config = ConfigFactory.load();
        setTrigger(config.getString("bot.trigger"));
        setBotNickname(config.getString("bot.identity.nickname"));
        setBotUsername(config.getString("bot.identity.username"));
        setBotIdent(config.getString("bot.identity.ident"));
        setBotPassword(config.getString("bot.identity.password"));
        setCtcpFinger(config.getString("bot.identity.finger"));
        setCtcpVersion(config.getString("bot.identity.version"));
        setPermissionDenied(config.getString("bot.lang.permission-denied"));
        setAutoNickChange(config.getBoolean("bot.reclaimnick"));
        setAutoReconnectServer(config.getBoolean("bot.reconnect"));
        setAutoAcceptInvite(config.getBoolean("bot.accept-invite"));
        setChannels(config.getStringList("bot.channels"));
        setDebug(config.getBoolean("bot.debug"));
        setServerHostame(config.getString("server.hostname"));
        setServerPort(config.getString("server.port"));
        setUseSSL(config.getBoolean("server.use-ssl"));
        setVerifySSL(config.getBoolean("server.verify-ssl"));
        setServerPassword(config.getString("server.password"));
        System.out.println("Config loaded!");
    }

    public boolean isAdmin(String s, String s1) {
        return false;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isAutoNickChange() {
        return autoNickChange;
    }

    public void setAutoNickChange(boolean autoNickChange) {
        this.autoNickChange = autoNickChange;
    }

    public boolean isAutoReconnectServer() {
        return autoReconnectServer;
    }

    public void setAutoReconnectServer(boolean autoReconnectServer) {
        this.autoReconnectServer = autoReconnectServer;
    }

    public boolean isAutoAcceptInvite() {
        return autoAcceptInvite;
    }

    public void setAutoAcceptInvite(boolean autoAcceptInvite) {
        this.autoAcceptInvite = autoAcceptInvite;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public boolean isVerifySSL() {
        return verifySSL;
    }

    public void setVerifySSL(boolean verifySSL) {
        this.verifySSL = verifySSL;
    }

    public boolean isEnableChatSocket() {
        return enableChatSocket;
    }

    public void setEnableChatSocket(boolean enableChatSocket) {
        this.enableChatSocket = enableChatSocket;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getServerHostame() {
        return serverHostame;
    }

    public void setServerHostame(String serverHostame) {
        this.serverHostame = serverHostame;
    }

    public String getServerPassword() {
        return serverPassword;
    }

    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getBotNickname() {
        return botNickname;
    }

    public void setBotNickname(String botNickname) {
        this.botNickname = botNickname;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public String getBotIdent() {
        return botIdent;
    }

    public void setBotIdent(String botIdent) {
        this.botIdent = botIdent;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public void setBotPassword(String botPassword) {
        this.botPassword = botPassword;
    }

    public String getCtcpFinger() {
        return ctcpFinger;
    }

    public void setCtcpFinger(String ctcpFinger) {
        this.ctcpFinger = ctcpFinger;
    }

    public String getCtcpVersion() {
        return ctcpVersion;
    }

    public void setCtcpVersion(String ctcpVersion) {
        this.ctcpVersion = ctcpVersion;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getLoggedChannels() {
        return loggedChannels;
    }

    public void setLoggedChannels(List<String> loggedChannels) {
        this.loggedChannels = loggedChannels;
    }

    public String getPermissionDenied() {
        return permissionDenied;
    }

    public void setPermissionDenied(String permissionDenied) {
        this.permissionDenied = permissionDenied;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public int getChatSocketPort() {
        return chatSocketPort;
    }

    public void setChatSocketPort(int chatSocketPort) {
        this.chatSocketPort = chatSocketPort;
    }

    public String getChatSocketPassword() {
        return chatSocketPassword;
    }

    public void setChatSocketPassword(String chatSocketPassword) {
        this.chatSocketPassword = chatSocketPassword;
    }
}
