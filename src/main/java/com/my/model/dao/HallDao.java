package com.my.model.dao;

import com.my.model.entities.Hall;

import java.util.List;

/**
 * Interface HallDao is used to access the information about halls from the database.
 *
 * @author Illia Kuznetsov
 */

public interface HallDao extends GenericDao<Hall>{

    /**
     * The method gets information about the hall by its name.
     *
     * @param name  - hall's name.
     */
    Hall findByName (String name);
}
