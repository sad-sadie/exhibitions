package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;

import javax.servlet.http.HttpServletRequest;

public class HomePage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
       return CommandExecutor.getHomePage(request);
    }
}
