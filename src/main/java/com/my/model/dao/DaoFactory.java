package com.my.model.dao;


import com.my.model.dao.implementations.DaoFactoryImpl;
import com.my.model.dao.exeptions.DBException;

public abstract class DaoFactory {

    private static volatile DaoFactory daoFactory;

    public abstract UserDao createUserDao() throws DBException;

    public abstract ExhibitionDao createExhibitionDao() throws DBException;

    public abstract HallDao createHallDao() throws DBException;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if(daoFactory == null) {
                    daoFactory = new DaoFactoryImpl();
                }
            }
        }
        return daoFactory;
    }
}
