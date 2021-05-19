package com.epam.controller;

import com.epam.command.CommandExecute;
import com.epam.command.CommandRequest;
import com.epam.command.RequestData;
import com.epam.command.RouteType;
import com.epam.command.factory.CommandFactory;
import com.epam.db.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            RequestData requestData = new RequestData(request);
            CommandFactory commandFactory = new CommandFactory();
            Optional<CommandRequest> command = commandFactory.defineCommand(requestData);

            if (command.isPresent()) {
                CommandExecute commandExecute = command.get().executeCommand(requestData);
                requestData.insertSessionAttributes(request);
                if (commandExecute.getRouteType().equals(RouteType.REDIRECT)) {
                    response.sendRedirect(request.getContextPath() + commandExecute.getPagePath());
                } else if (commandExecute.getRouteType().equals(RouteType.FORWARD)) {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getContextPath() + commandExecute.getPagePath());
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (IOException e) {
            LOGGER.error(new ServletException(e.getMessage()));
            //response.sendError();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }
}
