package com.my.model.dao.services;


import com.my.model.dao.ConnectionPool;
import com.my.model.dao.exeptions.DBException;
import com.my.model.entities.Exhibition;
import com.my.model.services.ExhibitionService;
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

public class ExhibitionServiceTest {

    private Connection connection;
    private ExhibitionService exhibitionService;

    @Before
    public void before() {
        try {
            connection = ConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new DBException("Cannot get connection", e);
        }
        exhibitionService = new ExhibitionService();
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
        final Date date = new Date(0);
        final Time time = new Time(0);
        Exhibition expected = Exhibition.builder()
                .id(320L)
                .theme("theme")
                .description("decr")
                .startDate(date)
                .endDate(date)
                .startTime(time)
                .endTime(time)
                .price(20.00)
                .build();


        exhibitionService.addExhibition(expected);

        Optional<Exhibition> actual = exhibitionService.findByTheme("theme");

        actual.ifPresent(exhibitionService::delete);

        Assert.assertNotNull(actual);
    }

    @Test
    public void findAll() {
        final Date date = new Date(0);
        final Time time = new Time(0);


        Exhibition exhibition = Exhibition.builder()
                .id(320L)
                .theme("theme")
                .description("decr")
                .startDate(date)
                .endDate(date)
                .startTime(time)
                .endTime(time)
                .price(20.00)
                .build();

        exhibitionService.addExhibition(exhibition);

        List<Exhibition> list = exhibitionService.findAllExhibitions();

        exhibitionService.delete(list.get(list.size() - 1));

        Assert.assertNotNull(list);
    }

    @Test
    public void deleteById() {
        Throwable exception = Assert.assertThrows(NoSuchElementException.class, () -> {
            final Date date = new Date(0);
            final Time time = new Time(0);
            Exhibition exhibition = Exhibition.builder()
                    .id(320L)
                    .theme("theme")
                    .description("decr")
                    .startDate(date)
                    .endDate(date)
                    .startTime(time)
                    .endTime(time)
                    .price(20.00)
                    .build();

            exhibitionService.addExhibition(exhibition);

            Optional<Exhibition> tmp = exhibitionService.findByTheme("theme");

            tmp.ifPresent(exhibitionService::delete);

            Optional<Exhibition> expected = exhibitionService.findByTheme("theme");

            if (!expected.isPresent()) {
                throw new NoSuchElementException("no such element");
            }

        });
        Assert.assertEquals(exception.getMessage(), "no such element");
    }

    @Test
    public void findExhibition() {
        final Date date = new Date(0);
        final Time time = new Time(0);
        Exhibition exhibition = Exhibition.builder()
                .id(320L)
                .theme("theme")
                .description("decr")
                .startDate(date)
                .endDate(date)
                .startTime(time)
                .endTime(time)
                .price(20.00)
                .build();

        exhibitionService.addExhibition(exhibition);

        Optional<Exhibition> expected = exhibitionService.findByTheme("theme");

        expected.ifPresent(exhibitionService::delete);

        Assert.assertNotNull(expected);
    }

    @Test
    public void update() {
        final Date date = new Date(0);
        final Time time = new Time(0);
        Exhibition exhibition = Exhibition.builder()
                .id(320L)
                .theme("theme")
                .description("decr")
                .startDate(date)
                .endDate(date)
                .startTime(time)
                .endTime(time)
                .price(20.00)
                .build();

        exhibitionService.addExhibition(exhibition);

        Optional<Exhibition> exhibition1 = exhibitionService.findByTheme("theme");

        exhibition = Exhibition.builder()
                .id(exhibition1.get().getId())
                .theme("theme1")
                .description("decr1")
                .startDate(date)
                .endDate(date)
                .startTime(time)
                .endTime(time)
                .price(20.00)
                .build();

        exhibitionService.update(exhibition);

        Optional<Exhibition> exhibition2 = exhibitionService.findByTheme("theme1");

        Assert.assertNotEquals(exhibition1, exhibition2);

        exhibition2.ifPresent(exhibitionService::delete);
    }

}