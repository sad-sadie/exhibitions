package com.my.controller;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@MultipartConfig
@WebServlet("/home")
public class Servlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(Servlet.class);

    @Override
    public void init() throws ServletException {
        log.info("Servlet.init");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession httpSession = request.getSession();

        if (httpSession.isNew()) {
            httpSession.setAttribute("user", null);
        }

        String page = "index.jsp";

        String commandName = request.getParameter("command");
        log.info("Servlet.doGet; commandName ==> " + commandName);

        if(commandName == null) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            Command command = CommandContainer.getCommand(commandName);
            page = command.execute(request);
            if (page.contains("redirect")) {
                response.sendRedirect(page.replace("redirect:",""));
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        log.info("Servlet.destroy");
    }
}
