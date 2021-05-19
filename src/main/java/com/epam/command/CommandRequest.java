package com.epam.command;

public interface CommandRequest {
    CommandExecute executeCommand(RequestData requestData);
}