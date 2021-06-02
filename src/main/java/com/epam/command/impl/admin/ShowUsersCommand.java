package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;

import java.util.List;

public class ShowUsersCommand implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = null;
        UserService userService = new UserServiceImpl();
        List<User> userList = userService.findAll();
        if (!userList.isEmpty()) {
            requestData.addSessionAttribute("users_list", userList);
            commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.USERS.getPath());
        } else {

        }
        return commandExecute;
    }
}
