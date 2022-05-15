package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.services.ExhibitionService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;

public class AddExhibition implements Command {

    private ExhibitionService exhibitionService;

    public AddExhibition(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @Override
    public String execute(HttpServletRequest request)  {
        String theme = request.getParameter("theme");
        String description = request.getParameter("description");
        Date startDate = null;
        Date endDate = null;
        Time startTime = null;
        Time endTime = null;
        double price = -1;
        if(!request.getParameter("startDate").equals("")) {
            startDate = Date.valueOf(request.getParameter("startDate"));
        }
        if(!request.getParameter("endDate").equals("")) {
            endDate = Date.valueOf(request.getParameter("endDate"));
        }
        if(!request.getParameter("startTime").equals("")) {
            startTime = Time.valueOf(request.getParameter("startTime") + ":00");
        }
        if(!request.getParameter("endTime").equals("")) {
            endTime = Time.valueOf(request.getParameter("endTime") + ":00");
        }
        String priceString = request.getParameter("price");
        if(!priceString.equals("")) {
            price = Double.parseDouble(priceString);
        }
        String[] hallsIDs = request.getParameterValues("chosenHalls");

        if(theme == null || theme.equals("")) {
            request.getSession().setAttribute("error", "exhibitionTheme");
            return "view/addExhibition.jsp";
        }
        if(description == null || description.equals("")) {
            request.getSession().setAttribute("error", "exhibitionDescription");
            return "view/addExhibition.jsp";
        }
        if(price <= 0) {
            request.getSession().setAttribute("error", "exhibitionPrice");
            return "view/addExhibition.jsp";
        }
        if(startDate == null) {
            request.getSession().setAttribute("error", "exhibitionStartDate");
            return "view/addExhibition.jsp";
        }
        if(endDate == null) {
            request.getSession().setAttribute("error", "exhibitionEndDate");
            return "view/addExhibition.jsp";
        }
        if(startDate.after(endDate) || startDate.before(new Date(System.currentTimeMillis()))) {
            request.getSession().setAttribute("error", "exhibitionDates");
            return "view/addExhibition.jsp";
        }
        if(startTime == null) {
            request.getSession().setAttribute("error", "exhibitionStartTime");
            return "view/addExhibition.jsp";
        }
        if(endTime == null) {
            request.getSession().setAttribute("error", "exhibitionEndTime");
            return "view/addExhibition.jsp";
        }
        if(startTime.after(endTime)) {
            request.getSession().setAttribute("error", "exhibitionTimes");
            return "view/addExhibition.jsp";
        }
        if(hallsIDs == null || hallsIDs.length == 0) {
            request.getSession().setAttribute("error", "exhibitionHalls");
            return "view/addExhibition.jsp";
        }

        if (exhibitionService.findByTheme(theme).isPresent()) {
            request.getSession().setAttribute("error", "exhibitionExists");
            return "view/addExhibition.jsp";
        }

        exhibitionService.addExhibition(theme, description, startDate, endDate, startTime, endTime, price);
        long exhibitionID = exhibitionService.findByTheme(theme).get().getId();
        exhibitionService.setHalls(exhibitionID, hallsIDs);
        return "redirect:view/addExhibition.jsp";
    }
}
