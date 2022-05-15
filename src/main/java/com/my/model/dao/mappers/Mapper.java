package com.my.model.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {

    public  T extractFromResultSet(ResultSet rs) throws SQLException;

}
