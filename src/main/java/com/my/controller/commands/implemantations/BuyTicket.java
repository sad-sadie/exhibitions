package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.entities.User;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class BuyTicket implements Command {

    private UserService userService;

    public BuyTicket(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long exhibitionID = Long.parseLong(request.getParameter("exhibitionId"));
        User user = (User) request.getSession().getAttribute("user");
        long userID = user.getId();

        userService.buyTicket(userID, exhibitionID);

        return "index.jsp";
    }
}
