package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class BuyTicket implements Command {

    private final UserService userService;

    public BuyTicket(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
      return CommandExecutor.buyTicket(request, userService);
    }
}
