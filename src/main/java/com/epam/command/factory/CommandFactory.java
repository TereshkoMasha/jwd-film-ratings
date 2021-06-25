package com.epam.command.factory;

import com.epam.command.AttributeName;
import com.epam.command.CommandRequest;
import com.epam.command.CommandType;
import com.epam.command.RequestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Optional;


public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);

    public Optional<CommandRequest> defineCommand(RequestData requestData) {
        Optional<CommandRequest> optionalCommand;
        try {
            String command = requestData.getRequestParameter(AttributeName.COMMAND);
            CommandType commandType = CommandType.valueOf(upper(command));
            optionalCommand = Optional.of(commandType.getCommand());

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception while command define", e);
            return Optional.of(CommandType.MAIN.getCommand());
        }
        return optionalCommand;
    }

    public static String upper(String name) {
        return name.replace("-", "_").toUpperCase(Locale.ROOT);
    }

}