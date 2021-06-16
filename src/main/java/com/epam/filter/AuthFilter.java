//package com.epam.filter;
//
//import com.epam.command.Destination;
//import com.epam.entity.User;
//import com.epam.entity.enums.UserRole;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = "/controller/*")
//public class AuthFilter implements Filter {
//    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        HttpSession session = httpRequest.getSession();
//        String redirectPage = null;
//        String command = httpRequest.getParameter("command");
//        if (command != null) {
//            if (session.getAttribute("user") != null) {
//                User user = (User) session.getAttribute("user");
//                UserRole userRole = user.getRole();
//                if (userRole != null) {
//                    switch (userRole) {
//                        case USER: {
//                            redirectPage = Destination.MAIN_PAGE.getPath();
//
//                        }
//                        case ADMIN: {
//                            redirectPage = Destination.MAIN_PAGE.getPath();
//                        }
//                    }
//                }
//            } else {
//                redirectPage = Destination.LOGIN.getPath();
//            }
//        }
//        if (redirectPage != null) {
//            httpResponse.sendRedirect(redirectPage);
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
