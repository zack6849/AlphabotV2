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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.UserLevel;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17";

    public static boolean isUrl(String s) {
        String url_regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(url_regex);
        Matcher m = p.matcher(s);
        return m.find();
    }

    public static int getRank(Channel chan, User user) {
        ArrayList<Integer> levels = new ArrayList<>();
        int highest = -1;
        for (UserLevel level : user.getUserLevels(chan)) {
            levels.add(level.ordinal());
        }
        for (int level : levels) {
            if (highest < level) {
                highest = level;
            }
        }
        return highest;
    }

    public static String munge(String word) {
        return word.replace("a", "\u00E0").replace("A", "\u00C0").replace("E", "\u00C8").replace("e", "\u00EB").replace("i", "\u00EF").replace("I", "\u00cf").replace("o", "\u00f8").replace("O", "\u0150").replace("u", "\u00FC").replace("U", "\u01D9").replace("y", "\u0177").replace("Y", "\u0178");
    }

    public static String getTitle(String link) {
        String response = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();
            conn.addRequestProperty("User-Agent", USER_AGENT);
            String type = conn.getContentType();
            int length = conn.getContentLength() / 1024;
            response = String.format("HTTP %s: %s", conn.getResponseCode(), conn.getResponseMessage());
            String info;
            if (type.contains("text") || type.contains("application")) {
                Document doc = Jsoup.connect(link).userAgent(USER_AGENT).followRedirects(true).get();
                String title = doc.title() == null || doc.title().isEmpty() ? "No title found!" : doc.title();
                info = String.format("%s - (Content Type: %s Size: %skb)", title, type, length);
                return info;
            }
            info = String.format("Content Type: %s Size: %skb", type, length);
            return info;

        } catch (IOException ex) {
            if (ex.getMessage().contains("UnknownHostException")) {
                return Colors.RED + "Unknown hostname!";
            }
            return response.isEmpty() ? Colors.RED + "An error occured" : response;
        }
    }

    public static String checkMojangServers() {
        String returns = null;
        try {
            URL url;
            url = new URL("http://status.mojang.com/check");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String result;
            while ((result = reader.readLine()) != null) {
                String a = result.replace("red", Colors.RED + "Offline" + Colors.NORMAL).replace("green", Colors.GREEN + "Online" + Colors.NORMAL).replace("[", "").replace("]", "");
                returns = a.replace("{", "").replace("}", "").replace(":", " is currently ").replace("\"", "").replaceAll(",", ", ");
            }
            reader.close();
        } catch (IOException e) {
            if (e.getMessage().contains("503")) {
                returns = "The minecraft status server is temporarily unavailable, please try again later";
            }
            if (e.getMessage().contains("404")) {
                returns = "Uhoh, it would appear as if the status page has been removed or relocated >_>";
            }

        }
        return returns;
    }

    public static String shortenUrl(String longUrl) {
        String shortened = null;
        try {
            URL url;
            url = new URL("http://is.gd/create.php?format=simple&url=" + longUrl);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            shortened = bufferedreader.readLine();
            bufferedreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shortened;
    }

    public static String checkServerStatus(InetAddress i, int port) {
        String returns = "Error.";
        try {
            //wow...i never actually used the port argument?
            Socket s = new Socket(i, port);
            DataInputStream SS_BF = new DataInputStream(s.getInputStream());
            DataOutputStream d = new DataOutputStream(s.getOutputStream());
            d.write(new byte[]
                    {
                            (byte) 0xFE, (byte) 0x01
                    });
            SS_BF.readByte();
            short length = SS_BF.readShort();
            StringBuilder sb = new StringBuilder();
            for (int in = 0; in < length; in++) {
                char ch = SS_BF.readChar();
                sb.append(ch);
            }
            String all = sb.toString().trim();
            System.out.println(all);
            String[] args1 = all.split("\u0000");
            if (args1[3].contains("§")) {
                returns = "MOTD: " + args1[3].replaceAll("§[a-m]", "").replaceAll("§[1234567890]", "") + "   players: [" + args1[4] + "/" + args1[5] + "]";
            } else {
                returns = "MOTD: " + args1[3] + "   players: [" + args1[4] + "/" + args1[5] + "]";
            }
        } catch (UnknownHostException e1) {
            returns = "the host you specified is unknown. check your settings.";
        } catch (IOException e1) {
            returns = "sorry, we couldn't reach this server, make sure that the server is up and has query enabled.";
        }
        return returns;
    }

    public static String google(String s) {
        try {
            String temp = String.format("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=%s", URLEncoder.encode(s, "UTF-8"));
            URL u = new URL(temp);
            URLConnection c = u.openConnection();
            c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17");
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String json = "";
            String tmp;
            while ((tmp = in.readLine()) != null) {
                json += tmp + "\n";
            }
            in.close();
            JsonElement jelement = new JsonParser().parse(json);
            JsonObject output = jelement.getAsJsonObject();
            output = output.getAsJsonObject("responseData").getAsJsonArray("results").get(0).getAsJsonObject();

            String title = StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeHtml4(output.get("titleNoFormatting").toString().replaceAll("\"", "")));
            String content = StringEscapeUtils.unescapeJava(StringEscapeUtils.unescapeHtml4(output.get("content").toString().replaceAll("\\s+", " ").replaceAll("\\<.*?>", "").replaceAll("\"", "")));
            String url = StringEscapeUtils.unescapeJava(output.get("url").toString().replaceAll("\"", ""));
            String result = String.format("Google: %s | %s | (%s)", title, content, url);
            if (result != null) {
                return result;
            } else {
                return "No results found for query " + s;
            }
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
