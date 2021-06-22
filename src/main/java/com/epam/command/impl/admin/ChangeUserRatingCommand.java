package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

public class ChangeUserRatingCommand implements CommandRequest {
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            List<User> users = (List<User>) requestData.getSessionAttribute("users_list");
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(id)) {
                    String rating = requestData.getRequestParameter("rating");
                    userService.updateUserRating(id, Double.parseDouble(rating));
                    users.set(i, userService.getById(id).get());
                    break;
                }
            }
            requestData.addSessionAttribute("users_list", users);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
    }
}
