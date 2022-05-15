package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.entities.Hall;
import com.my.model.services.HallService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetHalls implements Command {

    private HallService hallService = new HallService();

    public GetHalls(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        int currentPage = Integer.parseInt(request.getParameter("pageNum"));
        List<Hall> allHalls = hallService.findAllHalls();
        List<Hall> halls = new ArrayList<>();
        int index = (currentPage - 1) * 5;
        for(int i = index; i < Math.min( index + 5, allHalls.size()); ++i) {
            halls.add(allHalls.get(i));
        }
        int numberOfHalls = allHalls.size();
        int numberOfPages;

        if (numberOfHalls % 5 == 0) {
            numberOfPages = numberOfHalls / 5;
        } else {
            numberOfPages = (numberOfHalls / 5) + 1;
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("hallsToShow", halls);
        httpSession.setAttribute("numberOfPagesHalls", numberOfPages);
        httpSession.setAttribute("currentPageHalls", currentPage);

        return "view/halls.jsp";
    }
}
