package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {

    private final UserService userService;

    public Registration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request)  {
        return CommandExecutor.register(request, userService);

    }
}

