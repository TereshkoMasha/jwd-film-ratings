package com.epam.controller;

import com.epam.command.*;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestData requestData = new RequestData(request);
            CommandFactory commandFactory = new CommandFactory();
            Optional<CommandRequest> command = commandFactory.defineCommand(requestData);

            if (command.isPresent()) {
                CommandExecute commandExecute = command.get().executeCommand(requestData);
                if (commandExecute != null) {
                    requestData.insertSessionAndRequestAttributes(request);
                    if (commandExecute.getRouteType().equals(RouteType.REDIRECT)) {
                        response.sendRedirect(request.getContextPath() + commandExecute.getPagePath());
                    } else {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(commandExecute.getPagePath());
                        requestDispatcher.forward(request, response);
                    }
                } else {
                    request.getSession().setAttribute("nullPage", "Page not found");
                    response.sendRedirect(request.getContextPath() + Destination.ERROR.getPath());
                }
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(new ServletException(e.getMessage()));
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closePool();
    }
}