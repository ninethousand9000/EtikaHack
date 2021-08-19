package me.ninethousand.etikahack.api.util.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class WingsUtil {
    public static final ArrayList<String> usernames = new ArrayList<>();

    public static final void init() {
        try {
            URL url = new URL("https://pastebin.com/raw/HLAKE4Z6");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String s;

            while ((s = reader.readLine()) != null) {
                usernames.add(s.toLowerCase());
            }

        } catch (Exception ignored){

        }
    }
}
