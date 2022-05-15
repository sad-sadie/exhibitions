package com.my.model.dao.services;


import com.my.model.dao.ConnectionPool;
import com.my.model.dao.exeptions.DBException;
import com.my.model.dao.util.Md5Utils;
import com.my.model.entities.Hall;
import com.my.model.entities.User;
import com.my.model.entities.enums.Role;
import com.my.model.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserServiceTest {

    private Connection connection;
    private UserService userService;


    @Before
    public void before() {
        try {
            connection = ConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new DBException("Cannot get connection", e);
        }
        userService = new UserService();
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
    public void findAllUsers() {
        final User user = User.builder()
                .id(320L)
                .email("random_email")
                .login("random_login")
                .password(Md5Utils.toMd5("random_password"))
                .role(Role.USER)
                .build();

        userService.addUser(user.getEmail(), user.getLogin(), user.getPassword());

        List<User> list = userService.findAllUsers();

        userService.delete(list.get(list.size() - 1));

        Assert.assertNotNull(list);
    }

     @Test
    public void addUser() {
        final User user = User.builder()
                .id(320L)
                .email("random_email")
                .login("random_login")
                .password(Md5Utils.toMd5("random_password"))
                .role(Role.USER)
                .build();

        userService.addUser(user.getEmail(), user.getLogin(), user.getPassword());

        Optional<User> expected = userService.findByLogin("random_login");

        expected.ifPresent(userService::delete);

        Assert.assertNotNull(expected);
    }

    @Test
    public void findUser() {
        final User user = User.builder()
                .id(320L)
                .email("random_email")
                .login("random_login")
                .password("random_password")
                .role(Role.USER)
                .build();

        userService.addUser(user.getEmail(), user.getLogin(), user.getPassword());

        Optional<User> expected = userService.findUser("random_login", "random_password");

        expected.ifPresent(userService::delete);

        Assert.assertNotNull(expected);
    }

    @Test
    public void delete() {
        Throwable exception = Assert.assertThrows(NoSuchElementException.class, () -> {
            final User user = User.builder()
                    .id(320L)
                    .email("random_email")
                    .login("random_login")
                    .password(Md5Utils.toMd5("random_password"))
                    .role(Role.USER)
                    .build();

            userService.addUser(user.getEmail(), user.getLogin(), user.getPassword());

            Optional<User> expected = userService.findByLogin("random_login");

            expected.ifPresent(userService::delete);

            Optional<User> user1 = userService.findByLogin("random_login");

            if (!user1.isPresent()) {
                throw new NoSuchElementException("no such element");
            }
        });

        Assert.assertEquals(exception.getMessage(), "no such element");
    }

    @Test
    public void update() {
        User user = User.builder()
                .id(320L)
                .email("random_email")
                .login("random_login")
                .password(Md5Utils.toMd5("random_password"))
                .role(Role.USER)
                .build();

        userService.addUser(user);

        Optional<User> user1 = userService.findByLogin("random_login");

        user = User.builder()
                .id(user1.get().getId())
                .email("random_email1")
                .login("random_login1")
                .password(Md5Utils.toMd5("random_password1"))
                .role(Role.USER)
                .build();


        userService.update(user);

        Optional<User> user2 = userService.findByLogin("random_login1");

        Assert.assertNotEquals(user1, user2);

        user2.ifPresent(userService::delete);
    }
}