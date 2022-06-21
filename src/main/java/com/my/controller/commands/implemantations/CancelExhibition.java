package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;

public class CancelExhibition implements Command {

    private final ExhibitionService exhibitionService;

    public CancelExhibition(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return CommandExecutor.cancelExhibition(request, exhibitionService);
    }
}
