package com.epam.command.impl.common;

import com.epam.command.*;

public class ChangeLanguage implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String locale = requestData.getRequestParameter("locale");
        requestData.addSessionAttribute("locale", locale);

        return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
    }
}
