package com.my.controller.commands.implemantations;

import com.my.controller.commands.Command;
import com.my.model.dao.exeptions.DBException;

import javax.servlet.http.HttpServletRequest;

public class HomePage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws DBException {
        return "index.jsp";
    }
}
