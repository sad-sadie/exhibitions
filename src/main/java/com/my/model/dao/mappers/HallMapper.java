package com.my.model.dao.mappers;

import com.my.model.entities.Hall;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HallMapper implements Mapper<Hall> {

    public Hall extractFromResultSet(ResultSet rs) throws SQLException {
        return Hall.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
    }
}
