package com.my.model.dao.implementations;

import com.my.model.dao.constants.ExhibitionConstants;
import com.my.model.dao.ExhibitionDao;
import com.my.model.dao.mappers.ExhibitionMapper;
import com.my.model.dao.mappers.HallMapper;
import com.my.model.entities.Exhibition;
import com.my.model.dao.exeptions.DBException;
import com.my.model.entities.Hall;
import com.my.model.entities.User;
import com.my.model.services.UserService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ExhibitionDaoImpl implements ExhibitionDao {

    private static final Logger log = Logger.getLogger(ExhibitionDaoImpl.class);

    private final Connection con;
    private final ExhibitionMapper exhibitionMapper;
    private final UserService userService;

    public ExhibitionDaoImpl(Connection con) {
        this.con = con;
        exhibitionMapper = new ExhibitionMapper();
        userService = new UserService();
    }

    @Override
    public void add(Exhibition entity) {
        try (PreparedStatement ps = con.prepareStatement(ExhibitionConstants.ADD_EXHIBITION)) {
            int i = 1;
            ps.setString(i++, entity.getTheme());
            ps.setString(i++, entity.getDescription());
            ps.setDate(i++, Date.valueOf(entity.getStartDate()));
            ps.setDate(i++, Date.valueOf(entity.getEndDate()));
            ps.setTime(i++, Time.valueOf(entity.getStartTime()));
            ps.setTime(i++, Time.valueOf(entity.getEndTime()));
            ps.setDouble(i, entity.getPrice());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            log.error("Cannot save exhibition." + e);
            throw new DBException("Invalid exhibition input", e);
        }
    }

    @Override
    public Exhibition findById(Long id) {
        try (PreparedStatement ps = con.prepareStatement(ExhibitionConstants.FIND_EXHIBITION_BY_ID)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return exhibitionMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find exhibition." + e);
            throw new DBException("Cannot find exhibition", e);
        }
        return null;
    }

    @Override
    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new CopyOnWriteArrayList<>();

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(ExhibitionConstants.FIND_ALL_EXHIBITIONS);

            while (rs.next()) {
                Exhibition result = exhibitionMapper.extractFromResultSet(rs);
                exhibitions.add(result);
            }
        } catch (SQLException e) {
            log.error("Cannot find all exhibitions." + e);
            throw new DBException("Exception finding all exhibitions", e);
        }
        return exhibitions;
    }

    @Override
    public void update(Exhibition entity) {
        try (PreparedStatement ps = con.prepareStatement(ExhibitionConstants.UPDATE_EXHIBITION)) {
            int i = 1;
            ps.setString(i++, entity.getTheme());
            ps.setString(i++, entity.getDescription());
            ps.setDate(i++, Date.valueOf(entity.getStartDate()));
            ps.setDate(i++, Date.valueOf(entity.getEndDate()));
            ps.setTime(i++, Time.valueOf(entity.getStartTime()));
            ps.setTime(i++, Time.valueOf(entity.getEndTime()));
            ps.setDouble(i++, entity.getPrice());
            ps.setLong(i, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update exhibition." + e);
            throw new DBException("Cannot update exhibition", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement ps = con.prepareStatement(ExhibitionConstants.DELETE_EXHIBITION_BY_ID)) {
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete exhibition." + e);
            throw new DBException("Cannot delete exhibition", e);
        }
    }

    @Override
    public void deleteEntity(Exhibition entity) {
        delete(entity.getId());
    }

    @Override
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            log.error("Cannot close the connection." + e);
            throw new DBException("Cannot close the connection", e);
        }
    }

    @Override
    public Exhibition findByTheme(String theme) {
        try (PreparedStatement ps = con.prepareStatement(ExhibitionConstants.FIND_EXHIBITION_BY_THEME)) {
            ps.setString(1, theme);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return exhibitionMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find exhibition by theme." + e);
            throw new DBException("Cannot find exhibition", e);
        }
        return null;
    }

    @Override
    public void setHalls(long exhibitionID, String[] hallsIDs) {
        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.SET_HALLS)) {
            ps.setLong(1, exhibitionID);

            for (String hallID : hallsIDs) {
                ps.setLong(2, Long.parseLong(hallID));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Cannot set halls fot exhibition" + e);
            throw new DBException("Cannot set hall for exhibition", e);
        }
    }

    public List<Exhibition> getExhibitionsOnPageByParameter(int pageNum, String par) {
        List<Exhibition> exhibitions = new CopyOnWriteArrayList<>();
        String query;

        if(par.equals("theme")) {
            query = ExhibitionConstants.GET_BY_THEME;
        } else if(par.equals("price")) {
            query = ExhibitionConstants.GET_BY_PRICE;
        } else {
            query = ExhibitionConstants.GET_BY_DEFAULT;
        }

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, (pageNum - 1) * 5);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                exhibitions.add(exhibitionMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("Cannot get exhibitions by" + par + "." + e);
            throw new DBException("Cannot get exhibitions by " + par, e);
        }
        return exhibitions;
    }


    @Override
    public List<Exhibition> getExhibitionsOnPageByDate(int pageNum, LocalDate date) {
        List<Exhibition> exhibitions = new CopyOnWriteArrayList<>();

        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.GET_BY_DATE)){

            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2, (pageNum - 1) * 5);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                exhibitions.add(exhibitionMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("Cannot get exhibition on page by date." + e);
            throw new DBException("Cannot get exhibitions on page by date", e);
        }
        return exhibitions;
    }

    @Override
    public int getNumberOfExhibitionsByDate(LocalDate date) {
        int numberOfExhibitions = 0;

        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.GET_NUMBER_BY_DATE)) {

            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                numberOfExhibitions = rs.getInt(ExhibitionConstants.FIELD_NUMBER);
            }
        } catch (SQLException e) {
            log.error("Cannot get number of exhibitions." + e);
            throw new DBException("Cannot get numbers of exhibitions by date", e);
        }
        return numberOfExhibitions;
    }

    @Override
    public int getNumberOfExhibitions() {
        int numberOfExhibitions = 0;

        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.GET_NUMBER)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                numberOfExhibitions = rs.getInt(ExhibitionConstants.FIELD_NUMBER);
            }
        } catch (SQLException e) {
            log.error("Cannot get number of exhibitions" + e);
            throw new DBException("Cannot get number of exhibitions", e);
        }
        return numberOfExhibitions;
    }

    @Override
    public List<Hall> getHalls(long id) {
        List<Hall> halls = new CopyOnWriteArrayList<>();

        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.GET_HALLS)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                halls.add(new HallMapper().extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            log.error("Cannot get halls" + e);
            throw new DBException("Cannot get halls", e);
        }

        return halls;
    }

    @Override
    public int getNumberOfTicketsSoldByExhibitionID(long id) {
        int numberOfTickets = 0;

        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.GET_NUMBER_OF_TICKETS)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                numberOfTickets = rs.getInt(ExhibitionConstants.FIELD_NUMBER);
            }
        } catch (SQLException e) {
            log.error("Cannot get number of tickets sold." + e);
            throw new DBException("Cannot get number of tickets sold", e);
        }

        return numberOfTickets;
    }

    @Override
    public Map<String, Integer> getDetailedStatsByExhibitionID(long exhibitionID) {
        Map<String, Integer> detailedStats = new LinkedHashMap<>();

        try(PreparedStatement ps = con.prepareStatement(ExhibitionConstants.GET_DETAILED_STATS)) {

            List<User> users = userService.findAllUsers();
            ResultSet rs;

            for(User user : users) {
                ps.setLong(1, exhibitionID);
                ps.setLong(2, user.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    detailedStats.put(user.getLogin(), rs.getInt(ExhibitionConstants.FIELD_NUMBER_OF_BOUGHT_TICKETS));
                }
            }

        } catch (SQLException e) {
            log.error("Cannot get detailed statistics" + e);
            throw new DBException("Cannot get detailed statistics!", e);
        }
        return detailedStats.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
