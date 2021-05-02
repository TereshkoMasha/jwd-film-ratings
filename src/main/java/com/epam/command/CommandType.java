package com.epam.command;

import com.epam.command.impl.common.LoginCommand;
import com.epam.command.impl.common.LogoutCommand;
import com.epam.command.impl.common.MainCommand;

public enum CommandType {
    MAIN(new MainCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
