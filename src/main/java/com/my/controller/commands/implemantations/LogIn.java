package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class LogIn implements Command {

    private final UserService userService;

    public LogIn(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return CommandExecutor.login(request, userService);
    }
}