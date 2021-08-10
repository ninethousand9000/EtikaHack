package me.ninethousand.etikahack.api.command.commands;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.ModuleManager;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BindCommand extends Command {
    public BindCommand() {
        super("Bind", "bind <module> <key>");
    }


    @Override
    public void runCommand(ArrayList<String> args) {
        try {
            ModuleManager.getModulesByName(args.get(0)).setKey(Keyboard.getKeyIndex(args.get(1).toUpperCase()));
            sendClientMessageDefault("Binded " + args.get(0) + " to " + args.get(1));
        } catch (NoSuchElementException elementException) {
            sendClientMessageDefault("Module \" " + args.get(0) + "\" doesn't exist ");
        }
    }
}
