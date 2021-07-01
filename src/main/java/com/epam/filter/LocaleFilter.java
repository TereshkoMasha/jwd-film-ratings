package com.epam.filter;

import com.epam.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/controller/*")
public class LocaleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String locale = httpServletRequest.getParameter("locale");
        httpServletRequest.getRequestURI();
        httpServletRequest.getSession().setAttribute(AttributeName.LOCALE, locale);
        filterChain.doFilter(httpServletRequest, servletResponse);

    }
}
