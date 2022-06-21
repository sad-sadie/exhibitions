package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;

public class AddExhibition implements Command {

    private final ExhibitionService exhibitionService;

    public AddExhibition(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request)  {
        return CommandExecutor.addExhibition(request, exhibitionService);
    }
}
