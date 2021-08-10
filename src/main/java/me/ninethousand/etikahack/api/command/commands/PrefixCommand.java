package me.ninethousand.etikahack.api.command.commands;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.ModuleManager;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("Prefix", "prefix <key>");
    }

    @Override
    public void runCommand(ArrayList<String> args) {
        try {
            setPrefix(args.get(0));
            sendClientMessageDefault("Prefix set to " + args.get(0));
        } catch (Exception e) {
            sendClientMessageDefault("Couldnt Set Prefix");
        }
    }
}
