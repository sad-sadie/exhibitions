package com.my.controller.commands.implemantations;


import com.my.controller.commands.Command;
import com.my.model.entities.Exhibition;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

public class GetExhibitions implements Command {

    private ExhibitionService exhibitionService;

    public GetExhibitions(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        List<Exhibition> exhibitions;

        int currentPage = Integer.parseInt(request.getParameter("pageNum"));
        String sortType = request.getParameter("sortType");
        Date chosenDate = null;
        if (request.getParameter("chosenDate") != null
                && !request.getParameter("chosenDate").equals("")) {
            chosenDate = Date.valueOf(request.getParameter("chosenDate"));
        }

        if(sortType.equals("date") && chosenDate == null) {
            request.getSession().setAttribute("error", "badDate");
            return "index.jsp";
        }

        Date currentDate = new Date(System.currentTimeMillis());

        List<Exhibition> allExhibitions = exhibitionService.findAllExhibitions();

        for(Exhibition exhibition : allExhibitions) {
            if (exhibition.getEndDate().before(currentDate)) {
                exhibitionService.delete(exhibition);
            }
        }

        if(sortType.equals("date")) {
            exhibitions = exhibitionService.getExhibitionsOnPageByDate(currentPage, chosenDate);
        } else {
            exhibitions = exhibitionService.getExhibitionsSortedByParameter(currentPage, sortType);
        }

        if(chosenDate != null && request.getSession().getAttribute("exhibitionsByDate") == null) {
            request.getSession().setAttribute("exhibitionsByDate", exhibitionService.getExhibitionsOnPageByDate(currentPage, chosenDate));
        }


        int numberOfPages;
        int numberOfExhibitions;

        if (sortType.equals("date")) {
            numberOfExhibitions = exhibitionService.getNumberOfExhibitionsByDate(chosenDate);
        } else {
            numberOfExhibitions = exhibitionService.getNumberOfExhibitions();
        }

        if (numberOfExhibitions % 5 == 0) {
            numberOfPages = numberOfExhibitions / 5;
        } else {
            numberOfPages = (numberOfExhibitions / 5) + 1;
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("exhibitions", exhibitions);
        httpSession.setAttribute("numberOfPages", numberOfPages);
        httpSession.setAttribute("currentPage", currentPage);
        return "view/exhibitions.jsp";
    }
}
