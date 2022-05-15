package com.my.model.dao.implementations;


import com.my.model.dao.ConnectionPool;
import com.my.model.dao.DaoFactory;
import com.my.model.dao.ExhibitionDao;
import com.my.model.dao.HallDao;
import com.my.model.dao.UserDao;
import com.my.model.dao.exeptions.DBException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactoryImpl extends DaoFactory {

    private final DataSource dataSource = ConnectionPool.getDataSource();
    private UserDao userDao;
    private ExhibitionDao exhibitionDao;
    private HallDao hallDao;

    @Override
    public UserDao createUserDao() throws DBException {
        if (userDao == null) {
            userDao = new UserDaoImpl(getConnection());
        }
        return userDao;
    }

    @Override
    public ExhibitionDao createExhibitionDao() throws DBException {
        if (exhibitionDao == null) {
            exhibitionDao = new ExhibitionDaoImpl(getConnection());
        }
        return exhibitionDao;
    }

    @Override
    public HallDao createHallDao() throws DBException {
        if(hallDao == null) {
            hallDao = new HallDaoImpl(getConnection());
        }
        return hallDao;
    }

    private Connection getConnection() throws DBException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBException("Failed to get connection", e);
        }
    }
}
