package com.my.controller.commands;

import com.my.model.entities.Exhibition;
import com.my.model.entities.Hall;
import com.my.model.entities.User;
import com.my.model.entities.enums.Role;
import com.my.model.services.ExhibitionService;
import com.my.model.services.HallService;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CommandExecutor {
    public static String addExhibition(HttpServletRequest request, ExhibitionService exhibitionService) {
        String theme = request.getParameter("theme");
        String description = request.getParameter("description");
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        double price = -1;
        if(!request.getParameter("startDate").equals("")) {
            startDate = Date.valueOf(request.getParameter("startDate")).toLocalDate();
        }
        if(!request.getParameter("endDate").equals("")) {
            endDate = Date.valueOf(request.getParameter("endDate")).toLocalDate();
        }
        if(!request.getParameter("startTime").equals("")) {
            startTime = Time.valueOf(request.getParameter("startTime") + ":00").toLocalTime();
        }
        if(!request.getParameter("endTime").equals("")) {
            endTime = Time.valueOf(request.getParameter("endTime") + ":00").toLocalTime();
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
        if(startDate.isAfter(endDate) || startDate.isBefore(new Date(System.currentTimeMillis()).toLocalDate())) {
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
        if(startTime.isAfter(endTime)) {
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

    public static String addHall(HttpServletRequest request, HallService hallService) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if (name == null || name.equals("")) {
            request.getSession().setAttribute("error", "hallName");
            return "view/addHall.jsp";
        }
        if (description == null || description.equals("")) {
            request.getSession().setAttribute("error", "hallDescription");
            return "view/addHall.jsp";
        }
        if (hallService.findByName(name).isPresent()) {
            request.getSession().setAttribute("error", "hallExists");
            return "view/addHall.jsp";
        }

        hallService.addHall(name, description);
        List<Hall> halls = hallService.findAllHalls();
        request.getSession().setAttribute("halls", halls);
        return "redirect:view/addHall.jsp";
    }

    public static String buyTicket(HttpServletRequest request, UserService userService) {
        long exhibitionID = Long.parseLong(request.getParameter("exhibitionId"));
        User user = (User) request.getSession().getAttribute("user");
        long userID = user.getId();

        userService.buyTicket(userID, exhibitionID);

        return "index.jsp";
    }

    public static String cancelExhibition(HttpServletRequest request, ExhibitionService exhibitionService) {
        int id = Integer.parseInt(request.getParameter("canceledExhibitionId"));
        exhibitionService.deleteByID(id);
        return "index.jsp";
    }

    public static String chooseLanguage(HttpServletRequest req) {
        String language = req.getParameter("language");
        req.getSession().setAttribute("language", language);
        return "index.jsp";
    }

    public static String getDetailedStats(HttpServletRequest request, ExhibitionService exhibitionService) {

        Map<String, Integer> detailedStats;
        long exhibitionID = Long.parseLong(request.getParameter("exhibitionID"));

        detailedStats = exhibitionService.getDetailedStatisticsByExhibitionID(exhibitionID);

        request.getSession().setAttribute("detailedStats", detailedStats);

        return "view/detailedExhibitionStatistics.jsp";
    }

    public static String getExhibitions(HttpServletRequest request, ExhibitionService exhibitionService) {

        List<Exhibition> exhibitions;

        int currentPage = Integer.parseInt(request.getParameter("pageNum"));
        String sortType = request.getParameter("sortType");
        LocalDate chosenDate = null;
        if (request.getParameter("chosenDate") != null
                && !request.getParameter("chosenDate").equals("")) {
            chosenDate = Date.valueOf(request.getParameter("chosenDate")).toLocalDate();
        }

        if(sortType.equals("date") && chosenDate == null) {
            request.getSession().setAttribute("error", "badDate");
            return "index.jsp";
        }

        LocalDate currentDate = new Date(System.currentTimeMillis()).toLocalDate();

        List<Exhibition> allExhibitions = exhibitionService.findAllExhibitions();

        for(Exhibition exhibition : allExhibitions) {
            if (exhibition.getEndDate().isBefore(currentDate)) {
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

    public static String getHalls(HttpServletRequest request, HallService hallService) {

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

    public static String getStats(HttpServletRequest request, ExhibitionService exhibitionService) {

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

    public static String getHomePage(HttpServletRequest request) {
        return "index.jsp";
    }

    public static String login(HttpServletRequest request, UserService userService) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(login == null || login.equals("")) {
            request.getSession().setAttribute("error", "loginLogin");
            return "index.jsp";
        }
        if(password == null || password.equals("")) {
            request.getSession().setAttribute("error", "loginPassword");
            return "index.jsp";
        }

        Optional<User> user = userService.findUser(login, password);
        if (!user.isPresent()) {
            request.getSession().setAttribute("error", "badLogin");
            return "index.jsp";
        }


        request.getSession().setAttribute("user", user.get());
        return "redirect:/home";
    }

    public static String logOut(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return "index.jsp";
    }

    public static String register(HttpServletRequest request, UserService userService) {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (email == null || email.equals("")) {
            request.getSession().setAttribute("error", "registrationEmail");
            return "view/registration.jsp";
        }
        if (login == null || login.equals("")) {
            request.getSession().setAttribute("error", "registrationLogin");
            return "view/registration.jsp";
        }
        if (password == null || password.equals("")) {
            request.getSession().setAttribute("error", "registrationPassword");
            return "view/registration.jsp";
        }
        if (userService.findByLogin(login).isPresent()) {
            request.setAttribute("error", true);
            return "view/registration.jsp";
        }
        userService.addUser(email, login, password);
        if (userService.findByLogin(login).isPresent()) {
            User user = User.builder()
                    .email(email)
                    .login(login)
                    .password(password)
                    .role(Role.USER)
                    .build();
            userService.setCorrectID(user);
            request.getSession().setAttribute("user", user);
        }
        return "redirect:/home";
    }
}

