package com.my.model.services;

import com.my.model.dao.DaoFactory;
import com.my.model.dao.ExhibitionDao;
import com.my.model.entities.Exhibition;
import com.my.model.entities.Hall;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExhibitionService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ExhibitionDao exhibitionDao = daoFactory.createExhibitionDao();

    public List<Exhibition> findAllExhibitions() {
        return exhibitionDao.findAll();
    }

    public void addExhibition
            (String theme, String description, Date startDate, Date endDate, Time startTime, Time endTime, double price) {
        Exhibition exhibition = Exhibition.builder()
                .theme(theme)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .price(price)
                .build();

        exhibitionDao.add(exhibition);
    }

    public void addExhibition(Exhibition exhibition) {
        exhibitionDao.add(exhibition);
    }

    public Optional<Exhibition> findByTheme(String theme)  {
        return Optional.ofNullable(
                exhibitionDao.findByTheme(theme));
    }


    public Exhibition findById(long id) {
        return exhibitionDao.findById(id);
    }

    public void update(Exhibition exhibition) {
        exhibitionDao.update(exhibition);
    }

    public void setHalls(long exhibitionID, String[] hallsIDs) {
        exhibitionDao.setHalls(exhibitionID, hallsIDs);
    }

    public List<Exhibition> getExhibitionsSortedByParameter(int pageNum, String par) {
        if(par.equals("price") || par.equals("theme") || par.equals("default")) {
            return exhibitionDao.getExhibitionsOnPageByParameter(pageNum, par);
        } else {
            return null;
        }
    }

    public List<Exhibition> getExhibitionsOnPageByDate(int pageNum, Date date) {
        return exhibitionDao.getExhibitionsOnPageByDate(pageNum, date);
    }

    public int getNumberOfExhibitionsByDate(Date date) {
        return exhibitionDao.getNumberOfExhibitionsByDate(date);
    }

    public int getNumberOfExhibitions() {
        return exhibitionDao.getNumberOfExhibitions();
    }

    public List<Hall> getHalls(long id) {
        return exhibitionDao.getHalls(id);
    }

    public void delete(Exhibition exhibition) {
        exhibitionDao.deleteEntity(exhibition);
    }

    public void deleteByID(long id) {
        exhibitionDao.delete(id);
    }

    public int getNumberOfTicketsSoldByExhibitionID(long id) {
        return exhibitionDao.getNumberOfTicketsSoldByExhibitionID(id);
    }

    public Map<String, Integer> getDetailedStatisticsByExhibitionID(long exhibitionID) {
        return exhibitionDao.getDetailedStatsByExhibitionID(exhibitionID);
    }
}
