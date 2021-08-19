package me.ninethousand.etikahack.impl.modules.misc;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class Media extends Module {
    public static final Setting<String> name = new Setting<>("Name", "Etika");

    public Media() {
        addSettings(name);
    }
}
