package com.epam.command.factory;

import com.epam.command.Command;
import com.epam.command.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Optional;


public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public Optional<Command> defineCommand(Optional<String> commandType) {
        Optional<Command> command = Optional.empty();
        try {
            command = commandType.map(m -> CommandType.valueOf(upper(m)).getCommand());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception while command define", e);
        }
        return command;
    }

    public static String upper(String name) {
        return name.replace("-", "_").toUpperCase(Locale.ROOT);
    }

}
