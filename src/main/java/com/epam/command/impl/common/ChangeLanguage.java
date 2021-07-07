package com.epam.command.impl.common;

import com.epam.command.*;

public class ChangeLanguage implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        if (requestData.getRequestParametersValues().containsKey(AttributeName.LOCALE)) {
            String locale = requestData.getRequestParameter(AttributeName.LOCALE);
            requestData.addSessionAttribute(AttributeName.LOCALE, locale);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
    }
}
