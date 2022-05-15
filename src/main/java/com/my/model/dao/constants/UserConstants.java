package com.my.model.dao.constants;

public class UserConstants {

    private UserConstants() {
    }

    public static final String ADD_USER =
            "INSERT INTO user (email, login, password, role_id) VALUES (?, ?, ?, ?)";
    public static final String FIND_USER_BY_ID =
            "SELECT * FROM user WHERE id = ?";
    public static final String FIND_ALL_USERS =
            "SELECT * FROM user";
    public static final String UPDATE_USER =
            "UPDATE user SET email = ? , login = ?, password = ?, role_id = ? WHERE id = ?";
    public static final String DELETE_USER_BY_ID =
            "DELETE FROM user  WHERE id = ?";
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM user WHERE login = ? AND password = ?";
    public static final String FIND_USER_BY_LOGIN =
            "SELECT * FROM user WHERE login = ?";
    public static final String SET_ORDERS =
            "INSERT INTO orders VALUES(?, ?)";
    public static final String GET_ID =
            "SELECT id FROM user WHERE login = ?";

    public static final String FIELD_ID = "id";
}
