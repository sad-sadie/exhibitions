package com.my.controller.filters;

import com.my.model.entities.User;
import com.my.model.entities.enums.Role;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        if(!path.equals("/home") && !path.equals("/")) {
            int i = 1;
        }
        if(path.contains("addExhibition")
                || path.contains("addHall")
                || path.contains("getStatistics")
                || path.contains("getDetailedStats")) {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null || user.getRole().equals(Role.USER)) {
                logger.warn("User tried to access admin page without permission");
                response.sendRedirect("../index.jsp");

            } else {
                logger.info("Admin opened addExhibition/addHall/stats/detailedStats page");
                chain.doFilter(servletRequest,servletResponse);
            }
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
