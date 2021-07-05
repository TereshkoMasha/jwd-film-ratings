package com.epam.filter;

import com.epam.command.CommandType;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

@WebFilter(urlPatterns = "/controller/*")
public class AuthFilter implements Filter {
    private final List<String> adminCommands = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        String command = httpRequest.getParameter("command");
        if (command != null) {
            if (adminCommands.contains(command)) {
                if (session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    UserRole userRole = user.getRole();
                    if (userRole != null) {
                        if (userRole == UserRole.USER) {
                            httpRequest.setAttribute("error", "400. Bad request");
                            httpResponse.sendError(400, "Bad request");
                        } else if (userRole == UserRole.ADMIN) {
                            filterChain.doFilter(httpRequest, httpResponse);
                        }
                    }
                } else {
                    httpRequest.setAttribute("error", "400. Bad request");
                    httpResponse.sendError(400, "Bad request");
                }
            } else {
                filterChain.doFilter(httpRequest, httpResponse);
            }
        } else {
            filterChain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        EnumSet<CommandType> commandTypes = EnumSet.of(CommandType.CHANGE_RATING, CommandType.SHOW_USERS, CommandType.BAN_USER, CommandType.DELETE_USER_COMMENT);
        commandTypes.forEach(commandType -> adminCommands.add(lower(commandType.name())));
    }

    private static String lower(String name) {
        return name.replace("_", "-").toLowerCase(Locale.ROOT);
    }
}
