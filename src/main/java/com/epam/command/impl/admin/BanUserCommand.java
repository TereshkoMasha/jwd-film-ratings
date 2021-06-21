package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;

import java.util.List;

public class BanUserCommand implements CommandRequest {
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        boolean ban = Boolean.parseBoolean(requestData.getRequestParameter(AttributeName.BAN));
        if (ban) {
            userService.banUser(UserStatus.LOW, id);
        } else {
            userService.banUser(UserStatus.BANNED, id);
        }
        List<User> users = (List<User>) requestData.getSessionAttribute("users_list");
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, userService.getById(id).get());
                break;
            }
        }
        requestData.addSessionAttribute("users_list", users);
        return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
    }
}
