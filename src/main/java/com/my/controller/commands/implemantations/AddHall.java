package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.services.HallService;
import com.my.model.entities.Hall;
import com.my.model.dao.exeptions.DBException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddHall implements Command {

    private final HallService hallService;

    public AddHall(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public String execute(HttpServletRequest request) throws DBException {
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
}
