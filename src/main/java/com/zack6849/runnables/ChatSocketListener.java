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

package com.zack6849.runnables;

import org.pircbotx.PircBotX;

import java.net.ServerSocket;

public class ChatSocketListener extends Thread {
    private ServerSocket server;
    private int port;
    private PircBotX bot;

    public ChatSocketListener(PircBotX bot, int port) {
        this.port = port;
        this.bot = bot;
        System.out.println("Starting chat socket listener on port " + port);
    }

    public void run() {
        try {
            this.server = new ServerSocket(port);
            System.out.println("Accepting connections!");
            while (true) {
                new Thread(new ChatSocketHandler(server.accept(), bot)).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
