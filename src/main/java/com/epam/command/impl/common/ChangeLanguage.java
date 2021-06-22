package com.epam.command.impl.common;

import com.epam.command.*;

public class ChangeLanguage implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String locale = requestData.getRequestParameter("locale");
        if (locale.equals("3")) {
            requestData.addSessionAttribute("locale", "ru_RU");
        } else if (locale.equals("1")) {
            requestData.addSessionAttribute("locale", "by_BY");
        } else {
            requestData.addSessionAttribute("locale", "en_US");
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
    }
}
