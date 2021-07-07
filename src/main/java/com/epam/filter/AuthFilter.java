package com.epam.filter;

import com.epam.command.AttributeName;
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
    private final List<String> userCommands = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(false);

        String command = httpRequest.getParameter("command");

        if (command != null) {
            if (session != null && session.getAttribute(AttributeName.USER) != null) {
                if (adminCommands.contains(command)) {
                    User user = (User) session.getAttribute(AttributeName.USER);
                    UserRole userRole = user.getRole();
                    if (userRole != null) {
                        if (userRole == UserRole.USER) {
                            httpRequest.setAttribute(AttributeName.ERROR, "403. Forbidden");
                            httpResponse.sendError(403, "Forbidden");
                        } else if (userRole == UserRole.ADMIN) {
                            filterChain.doFilter(httpRequest, httpResponse);
                        }
                    }
                } else {
                    filterChain.doFilter(httpRequest, httpResponse);
                }
            } else if (userCommands.contains(command)) {
                httpRequest.setAttribute(AttributeName.ERROR, "401. Unauthorized");
                httpResponse.sendError(401, "Unauthorized");
            } else if (adminCommands.contains(command)) {
                httpRequest.setAttribute(AttributeName.ERROR, "403. Forbidden");
                httpResponse.sendError(403, "Forbidden");
            } else {
                filterChain.doFilter(httpRequest, httpResponse);
            }
        } else {
            filterChain.doFilter(httpRequest, httpResponse);
        }

    }


    /**
     * Create a {@link EnumSet<CommandType>} from admin-only commands
     */
    @Override
    public void init(FilterConfig filterConfig) {
        EnumSet<CommandType> commandTypes = EnumSet.of(CommandType.CHANGE_RATING, CommandType.SHOW_USERS, CommandType.BAN_USER, CommandType.DELETE_USER_COMMENT);
        commandTypes.forEach(commandType -> adminCommands.add(lower(commandType.name())));

        EnumSet<CommandType> commandTypesUser = EnumSet.of(CommandType.LEAVE_COMMENT, CommandType.VIEW_USER_PROFILE, CommandType.LOGOUT);
        commandTypesUser.forEach(commandType -> userCommands.add(lower(commandType.name())));
    }

    private static String lower(String name) {
        return name.replace("_", "-").toLowerCase(Locale.ROOT);
    }
}
