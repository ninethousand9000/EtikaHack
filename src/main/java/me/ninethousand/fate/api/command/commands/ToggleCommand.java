package me.ninethousand.fate.api.command.commands;

import me.ninethousand.fate.api.command.Command;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", new String[]{""});
    }

    @Override
    public void doCommand(String[] commands) {
        Command.sendClientMessageDefault("Toggled");
    }
}
