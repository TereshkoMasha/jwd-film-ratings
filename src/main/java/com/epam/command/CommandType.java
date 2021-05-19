package com.epam.command;

import com.epam.command.impl.common.LoginCommand;
import com.epam.command.impl.common.LogoutCommand;
import com.epam.command.impl.common.MainCommand;

public enum CommandType {
    MAIN(new MainCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand());

    CommandRequest command;

    CommandType(CommandRequest command) {
        this.command = command;
    }

    public CommandRequest getCommand() {
        return command;
    }
}
