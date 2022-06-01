package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.services.UserService;
import com.my.model.entities.enums.Role;
import com.my.model.entities.User;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {

    private final UserService userService;

    public Registration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request)  {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (email == null || email.equals("")) {
            request.getSession().setAttribute("error", "registrationEmail");
            return "view/registration.jsp";
        }
        if (login == null || login.equals("")) {
            request.getSession().setAttribute("error", "registrationLogin");
            return "view/registration.jsp";
        }
        if (password == null || password.equals("")) {
            request.getSession().setAttribute("error", "registrationPassword");
            return "view/registration.jsp";
        }
        if (userService.findByLogin(login).isPresent()) {
            request.setAttribute("error", true);
            return "view/registration.jsp";
        }
        userService.addUser(email, login, password);
        if (userService.findByLogin(login).isPresent()) {
            User user = User.builder()
                    .email(email)
                    .login(login)
                    .password(password)
                    .role(Role.USER)
                    .build();
            userService.setCorrectID(user);
            request.getSession().setAttribute("user", user);
        }
        return "redirect:/home";
    }
}

