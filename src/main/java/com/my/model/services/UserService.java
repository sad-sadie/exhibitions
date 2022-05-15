package com.my.model.services;

import com.my.model.dao.DaoFactory;
import com.my.model.dao.UserDao;
import com.my.model.dao.util.Md5Utils;
import com.my.model.entities.enums.Role;
import com.my.model.entities.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    UserDao userDao = daoFactory.createUserDao();

    public List<User> findAllUsers() {
       return userDao.findAll();
    }

    public void addUser(String email, String login, String password) {
        password = Md5Utils.toMd5(password);
        User user = User.builder()
                .email(email)
                .login(login)
                .password(password)
                .role(Role.USER)
                .build();

        userDao.add(user);
    }

    public void addUser(User user) {
        userDao.add(user);
    }

    public Optional<User> findUser(String login, String password) {
        return Optional.ofNullable(
                userDao.findByLoginAndPassword(login, password)
        );
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(
                    userDao.findByLogin(login)
        );
    }

    public User findById(long id) {
        return userDao.findById(id);
    }

    public void update(User user) {
       userDao.update(user);
    }

    public void delete(User user) {
        userDao.deleteEntity(user);
    }


    public void buyTicket(long userID, long exhibitionID)  {
        userDao.buyTicket(userID, exhibitionID);
    }

    public void setCorrectID(User user) {
        userDao.setCorrectID(user);
    }
}
