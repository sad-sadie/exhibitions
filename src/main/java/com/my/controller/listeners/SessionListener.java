package com.my.controller.listeners;

import com.my.model.entities.Hall;
import com.my.model.services.HallService;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HallService hallService = new HallService();
        List<Hall> halls = hallService.findAllHalls();
        httpSessionEvent.getSession().setAttribute("halls", halls);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
