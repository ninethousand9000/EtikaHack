package me.ninethousand.fate.api.command;

import me.ninethousand.fate.api.command.commands.BindCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class CommandManager {
    protected static final ArrayList<Command> commands = new ArrayList<>();

    public static void init() {
        commands.addAll(Arrays.asList(
            new BindCommand()
        ));

        commands.sort(CommandManager::order);
    }

    private static int order(Command command1, Command command2) {
        return command1.getLabel().compareTo(command2.getLabel());
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static <T extends Command> Command getCommand(Class<T> clazz) {
        for (Command command : commands) {
            if (command.getClass().isAssignableFrom(clazz)) return command;
        }
        throw new NoSuchElementException();
    }
}
