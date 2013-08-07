/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zack6849.alphabot.api;

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
        return "";
    }
}
