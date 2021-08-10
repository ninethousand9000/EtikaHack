package me.ninethousand.etikahack.api.social;

import java.util.ArrayList;

public class FriendManager {
    protected static final ArrayList<String> friends = new ArrayList<>();

    public static void init() {

    }

    public static ArrayList<String> getFriends() {
        return friends;
    }

    public static void add(String name) {
        friends.add(name);
    }

    public static void del(String name) {
        friends.remove(name);
    }

    public static boolean isFriend(String name) {
        for (String friend : friends) {
            if (friend.equals(name)) return true;
        }

        return false;
    }
}
