package com.my.model.dao.mappers;

import com.my.model.entities.enums.Role;
import com.my.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {

    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .role(Role.values()[rs.getInt("role_id")])
                .build();
    }
}
