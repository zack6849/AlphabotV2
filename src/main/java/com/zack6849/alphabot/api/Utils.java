/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.api;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Zack
 */
public class Utils
{

    public static boolean isUrl(String s)
    {
        String url_regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(url_regex);
        Matcher m = p.matcher(s);
        if (m.find())
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static String getTitle(String link)
    {
        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            Future<Response> future = client.prepareGet(link).setFollowRedirects(true).execute();
            Response response = future.get();
            String output = response.getResponseBody("UTF-8");
            Pattern p = Pattern.compile("<title>.+</title>");
            Matcher m;
            for(String line : output.split("\n")){
                m = p.matcher(line);
                if(m.find()){
                    return line.split("<title>")[1].split("</title>")[0];
                }
            }
            return "No title found???";
        } catch (Exception ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
