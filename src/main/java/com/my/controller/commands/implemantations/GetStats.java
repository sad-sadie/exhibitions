package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.entities.Exhibition;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetStats implements Command {

    private ExhibitionService exhibitionService;

    public GetStats(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }


    @Override
    public String execute(HttpServletRequest request) {

        Map<Exhibition, Integer> exhibitionsStats = new LinkedHashMap<>();
        List<Exhibition> exhibitions;
        int ticketsSold;

        exhibitions = exhibitionService.findAllExhibitions();

        for (Exhibition exhibition : exhibitions) {
            ticketsSold = exhibitionService.getNumberOfTicketsSoldByExhibitionID(exhibition.getId());
            exhibitionsStats.put(exhibition, ticketsSold);
        }

        request.getSession().setAttribute("exhibitionsStats", exhibitionsStats);

        return "view/exhibitionStatistics.jsp";
    }
}
