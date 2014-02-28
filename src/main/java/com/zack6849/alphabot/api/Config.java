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

package com.zack6849.alphabot.api;

import com.notoriousdev.yamlconfig.YamlConfig;
import com.notoriousdev.yamlconfig.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class Config {
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
    private List<String> channels;
    private List<String> loggedChannels;
    private String permissionDenied;
    private Properties properties;
    private int chatSocketPort;

    public void load() {
        YamlConfig conf = new YamlConfig(new File("").getAbsoluteFile(), "config.yml");
        FileConfiguration config = conf.getConfig();
        setBotNickname(config.getString("bot-nickname"));
        setBotUsername(config.getString("bot-username"));
        setBotIdent(config.getString("bot-ident"));
        setBotPassword(config.getString("bot-password"));
        setCtcpFinger(config.getString("ctcp-finger-reply"));
        setCtcpVersion(config.getString("ctcp-version-reply"));
        setTrigger(config.getString("bot-trigger"));
        setPermissionDenied(config.getString("permission-denied"));
        setAutoNickChange(config.getBoolean("auto-nickchange"));
        setAutoReconnectServer(config.getBoolean("auto-reconnect"));
        setAutoRejoinChannel(config.getBoolean("auto-rejoin"));
        setAutoAcceptInvite(config.getBoolean("auto-accept-invite"));
        setChannels(config.getStringList("channels"));
        setLoggedChannels(config.getStringList("channels-log"));
        setDebug(config.getBoolean("debug"));
        setServerHostame(config.getString("server-hostname"));
        setServerPort(config.getString("server-port"));
        setUseSSL(config.getBoolean("use-ssl"));
        setVerifySSL(config.getBoolean("verify-ssl"));
        setServerPassword(config.getString("server-password"));
        setEnableChatSocket(config.getBoolean("enable-chat-socket"));
        setChatSocketPort(config.getInt("chat-socket-port"));
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

    public boolean isAutoRejoinChannel() {
        return autoRejoinChannel;
    }

    public void setAutoRejoinChannel(boolean autoRejoinChannel) {
        this.autoRejoinChannel = autoRejoinChannel;
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
}
