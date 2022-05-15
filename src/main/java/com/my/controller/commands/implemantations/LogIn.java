package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.services.UserService;
import com.my.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LogIn implements Command {

    private final UserService userService;

    public LogIn(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(login == null || login.equals("")) {
            request.getSession().setAttribute("error", "loginLogin");
            return "index.jsp";
        }
        if(password == null || password.equals("")) {
            request.getSession().setAttribute("error", "loginPassword");
            return "index.jsp";
        }

        Optional<User> user = userService.findUser(login, password);
        if (!user.isPresent()) {
            request.getSession().setAttribute("error", "badLogin");
            return "index.jsp";
        }


        request.getSession().setAttribute("user", user.get());
        return "redirect:/home";
    }
}