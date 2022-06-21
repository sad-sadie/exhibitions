package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandExecutor;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;

public class GetStats implements Command {

    private ExhibitionService exhibitionService;

    public GetStats(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        return CommandExecutor.getStats(request, exhibitionService);
    }
}
