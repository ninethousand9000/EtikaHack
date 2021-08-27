package me.ninethousand.etikahack.api.user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class HardwareUtil {
    public static final ArrayList<String> ids = new ArrayList<>();

    public static final void init() {
        try {
            URL url = new URL("https://pastebin.com/raw/g08A6TRH");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String s;

            while ((s = reader.readLine()) != null) {
                ids.add(s);
            }

        } catch (Exception ignored){

        }
    }
}
