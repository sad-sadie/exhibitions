package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


public class GetDetailedStats implements Command {

    private ExhibitionService exhibitionService;

    public GetDetailedStats(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        Map<String, Integer> detailedStats;
        long exhibitionID = Long.parseLong(request.getParameter("exhibitionID"));

        detailedStats = exhibitionService.getDetailedStatisticsByExhibitionID(exhibitionID);

        request.getSession().setAttribute("detailedStats", detailedStats);

        return "view/detailedExhibitionStatistics.jsp";
    }
}
