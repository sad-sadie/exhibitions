package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.HallService;

import javax.servlet.http.HttpServletRequest;

public class AddHall implements Command {

    private final HallService hallService;

    public AddHall(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return CommandExecutor.addHall(request, hallService);
    }
}
