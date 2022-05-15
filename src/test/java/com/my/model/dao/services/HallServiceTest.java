package com.my.model.dao.services;


import com.my.model.dao.ConnectionPool;
import com.my.model.dao.exeptions.DBException;
import com.my.model.entities.Exhibition;
import com.my.model.entities.Hall;
import com.my.model.services.HallService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HallServiceTest {

    private Connection connection;
    private HallService hallService;

    @Before
    public void before() {
        try {
            connection = ConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new DBException("Cannot get connection", e);
        }
        hallService = new HallService();
    }

    @After
    public void after() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException("Cannot close connection", e);
        }
    }

    @Test
    public void add() {
        Hall expected = Hall.builder()
                .id(320L)
                .name("name")
                .description("description")
                .build();

        hallService.addHall(expected);

        Optional<Hall> actual = hallService.findByName("name");

        actual.ifPresent(hallService::delete);

        Assert.assertNotNull(actual);
    }

    @Test
    public void findAll() {
        Hall expected = Hall.builder()
                .id(320L)
                .name("name")
                .description("description")
                .build();

        hallService.addHall(expected);

        List<Hall> list = hallService.findAllHalls();

        hallService.delete(list.get(list.size() - 1));

        Assert.assertNotNull(list);
    }

    @Test
    public void deleteById() {
        Throwable exception = Assert.assertThrows(NoSuchElementException.class, () -> {
            Hall expected = Hall.builder()
                    .id(320L)
                    .name("name")
                    .description("description")
                    .build();

            hallService.addHall(expected);

            Optional<Hall> tmp = hallService.findByName("name");

            tmp.ifPresent(hallService::delete);

            Optional<Hall> hall = hallService.findByName("name");

            if (!hall.isPresent()) {
                throw new NoSuchElementException("no such element");
            }

        });
        Assert.assertEquals(exception.getMessage(), "no such element");
    }

    @Test
    public void findHall() {
        Hall expected = Hall.builder()
                .id(320L)
                .name("name")
                .description("description")
                .build();

        hallService.addHall(expected);

        Optional<Hall> hall = hallService.findByName("name");

        hall.ifPresent(hallService::delete);

        Assert.assertNotNull(hall);
    }

    @Test
    public void update() {
        Hall hall = Hall.builder()
                .id(320L)
                .name("name")
                .description("description")
                .build();

        hallService.addHall(hall);

        Optional<Hall> hall1 = hallService.findByName("name");

        hall = Hall.builder()
                .id(hall1.get().getId())
                .name("name1")
                .description("description1")
                .build();

        hallService.update(hall);

        Optional<Hall> hall2 = hallService.findByName("name1");

        Assert.assertNotEquals(hall1, hall2);

        hall2.ifPresent(hallService::delete);
    }
}