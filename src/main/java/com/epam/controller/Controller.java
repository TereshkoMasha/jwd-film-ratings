package com.epam.controller;

import com.epam.command.Command;
import com.epam.command.RoteType;
import com.epam.command.factory.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {

            var commandFactory = new CommandFactory();
            String commandName = request.getParameter("command");
            Optional<String> requestCommand = Optional.ofNullable(commandName);
            Optional<Command> command = commandFactory.defineCommand(requestCommand);

            if (command.isPresent()) {
                var commandExecute = command.get().executeCommand(request);
                if (commandExecute.getRoteType().equals(RoteType.REDIRECT)) {
                    response.sendRedirect(request.getContextPath() + commandExecute.getPagePath());
                } else if (commandExecute.getRoteType().equals(RoteType.FORWARD)) {
                    var requestDispatcher = request.getRequestDispatcher(commandExecute.getPagePath());
                    requestDispatcher.forward(request, response);
                } else {
                    //response.sendError();
                }
            }
        } catch (IOException e) {
            LOGGER.error(new ServletException(e.getMessage()));
        }
    }
}
