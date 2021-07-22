package me.ninethousand.fate.impl.modules.combat;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(category = ModuleCategory.Combat, bind = Keyboard.KEY_O)
public class TestModule extends Module {

}
