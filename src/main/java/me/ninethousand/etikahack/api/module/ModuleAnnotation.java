package me.ninethousand.etikahack.api.module;

import org.lwjgl.input.Keyboard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleAnnotation {
    String description() default "None";
    ModuleCategory category();
    int bind() default Keyboard.KEY_NONE;
    boolean enabledByDefault() default false;
    boolean alwaysEnabled() default false;
}
