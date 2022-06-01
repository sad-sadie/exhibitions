package com.my.model.services;

import com.my.model.dao.DaoFactory;
import com.my.model.dao.HallDao;
import com.my.model.entities.Hall;

import java.util.List;
import java.util.Optional;

public class HallService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final HallDao hallDao = daoFactory.createHallDao();

    public List<Hall> findAllHalls() {
        return hallDao.findAll();
    }

    public void addHall(String name, String description) {
        Hall hall = Hall.builder()
                .name(name)
                .description(description)
                .build();

        hallDao.add(hall);
    }

    public void addHall(Hall hall) {
        hallDao.add(hall);
    }

    public Optional<Hall> findByName(String name) {
        return Optional.ofNullable(
                hallDao.findByName(name));
    }

    public Hall findById(long id) {
        return hallDao.findById(id);
    }

    public void update(Hall hall) {
        hallDao.update(hall);
    }

    public void delete(Hall hall) {
        hallDao.deleteEntity(hall);
    }

}
