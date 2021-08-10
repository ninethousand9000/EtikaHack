package me.ninethousand.etikahack.api.command.commands;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.social.FriendManager;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FriendCommand extends Command {
    public FriendCommand() {
        super("Friend", "friend <name>");
    }


    @Override
    public void runCommand(ArrayList<String> args) {
        try {
            if (FriendManager.isFriend(args.get(0))) {
                FriendManager.del(args.get(0));
                sendClientMessageDefault("Removed " + args.get(0));
            }
            else {
                FriendManager.add(args.get(0));
                sendClientMessageDefault("Friended " + args.get(0));
            }


        } catch (NoSuchElementException elementException) {
            sendClientMessageDefault("Error couldn't friend " + args.get(0));
        }
    }
}
