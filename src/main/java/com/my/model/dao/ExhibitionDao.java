package com.my.model.dao;

import com.my.model.entities.Exhibition;
import com.my.model.entities.Hall;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Interface ExhibitionDao is used to access the information about exhibitions from the database.
 *
 * @author Illia Kuzntesov
 */

public interface ExhibitionDao extends GenericDao<Exhibition> {

    /**
     * The method gets information about the exhibition by its name.
     *
     * @param name - exhibition's name.
     */
    Exhibition findByTheme(String name);


    /**
     * The method sets halls for the chosen exhibition.
     *
     * @param exhibitionID - exhibition's id.
     * @param hallsIDs - halls' ids.
     */
    void setHalls(long exhibitionID, String[] hallsIDs);


    /**
     * The method gets information about the exhibition for page display by a parameter.
     *
     * @param pageNum - number of the page to be shown.
     * @param par - chosen parameter.
     */
    List<Exhibition> getExhibitionsOnPageByParameter(int pageNum, String par);


    /**
     * The method gets information about the exhibition for page display by a date.
     *
     * @param pageNum - number of the page to be shown.
     * @param date - chosen date.
     */
    List<Exhibition> getExhibitionsOnPageByDate(int pageNum, LocalDate date);


    /**
     * The method gets number of exhibitions for page display.
     */
    int getNumberOfExhibitions();


    /**
     * The method gets number of exhibitions for page display by date.
     */
    int getNumberOfExhibitionsByDate(LocalDate date);


    /**
     * The method gets all the halls of the exhibition by its id.
     *
     * @param id - exhibition's id.
     */
    List<Hall> getHalls(long id);


    /**
     * The method gets the number of tickets sold of the exhibition by its id.
     *
     * @param id - exhibition's id.
     */
    int getNumberOfTicketsSoldByExhibitionID(long id);


    /**
     * The method gets detailed statistics of the exhibition by its id.
     *
     * @param exhibitionID - exhibition's id.
     */
    Map<String, Integer> getDetailedStatsByExhibitionID(long exhibitionID);
}
