package com.my.model.dao.mappers;

import com.my.model.entities.Exhibition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExhibitionMapper implements Mapper<Exhibition> {

    public  Exhibition extractFromResultSet(ResultSet rs) throws SQLException {
        return Exhibition.builder()
                .id(rs.getLong("id"))
                .theme(rs.getString("theme"))
                .description(rs.getString("description"))
                .startDate(rs.getDate("start_date"))
                .endDate(rs.getDate("end_date"))
                .startTime(rs.getTime("start_time"))
                .endTime(rs.getTime("end_time"))
                .price(rs.getDouble("price"))
                .build();
    }
}
