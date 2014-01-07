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

package com.zack6849.runnables;

import org.pircbotx.PircBotX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatSocketHandler extends Thread {
    PircBotX bot;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;

    public ChatSocketHandler(Socket socket, PircBotX bot) {
        this.socket = socket;
        this.bot = bot;
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection from " + socket.getRemoteSocketAddress());
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    bot.sendRaw().rawLineNow(line);
                } else {
                    writer.write("Invalid format!\r\n");
                    writer.flush();
                }
            }
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
