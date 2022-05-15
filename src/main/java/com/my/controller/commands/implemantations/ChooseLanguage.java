package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class ChooseLanguage implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        String language = req.getParameter("language");
        req.getSession().setAttribute("language", language);
        return "index.jsp";
    }
}
