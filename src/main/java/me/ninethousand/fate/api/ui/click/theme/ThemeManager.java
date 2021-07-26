package me.ninethousand.fate.api.ui.click.theme;

import me.ninethousand.fate.impl.ui.click.themes.DefaultTheme;

import java.util.ArrayList;
import java.util.Collections;

public final class ThemeManager {
    private static final ArrayList<Theme> themes = new ArrayList<>();

    public static void init() {
        themes.addAll(Collections.singletonList(
                new DefaultTheme()
        ));
    }

    public static Theme getThemeByName(String name) {
        return themes.stream()
                .filter(theme -> theme.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
