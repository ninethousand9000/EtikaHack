package me.ninethousand.fate.api.ui.click.theme;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ThemeAnnotation {
    String name();
    int width();
    int height();
}
