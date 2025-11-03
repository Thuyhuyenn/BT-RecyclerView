package com.example.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class FavoriteManager {
    private static final String PREFS_NAME = "favorite_prefs";
    private static final String KEY_FAVORITES = "favorites";

    public static boolean isFavorite(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet(KEY_FAVORITES, new HashSet<>());
        return favorites.contains(name);
    }

    public static void toggleFavorite(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<>()));

        if (favorites.contains(name)) {
            favorites.remove(name);
        } else {
            favorites.add(name);
        }

        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }
}