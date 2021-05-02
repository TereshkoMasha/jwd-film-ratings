package com.epam.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandExecute executeCommand(HttpServletRequest request);
}