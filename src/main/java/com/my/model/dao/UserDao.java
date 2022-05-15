package com.my.model.dao;

import com.my.model.entities.User;

/**
 * Interface UseDao is used to access the information about users from the database.
 *
 * @author Illia Kuznetsov
 */

public interface UserDao extends GenericDao<User> {


    /**
     * The method gets information about the user by its login and password.
     *
     * @param username - user's login.
     * @param password - user's password.
     */
    User findByLoginAndPassword(String username, String password);

    /**
     * The method gets information about the user by its login.
     *
     * @param username - user's username.
     */
    User findByLogin (String username);


    /**
     * The method performs the user's purchase of the ticket for the chosen exhibition.
     *
     * @param userID - id of the user, who is buying the ticket.
     * @param exhibitionID - id of the chosen exhibition.
     */
    void buyTicket(long userID, long exhibitionID);


    /**
     * The method sets correct id for a newly created user.
     *
     * @param user - user, whose id is being corrected.
     */
    void setCorrectID(User user);
}
