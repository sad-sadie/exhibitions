package com.my.model.dao.implementations;

import com.my.model.dao.constants.HallConstants;
import com.my.model.dao.HallDao;
import com.my.model.dao.mappers.HallMapper;
import com.my.model.entities.Hall;
import com.my.model.dao.exeptions.DBException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HallDaoImpl implements HallDao {

    private static final Logger log = Logger.getLogger(HallDaoImpl.class);

    private final Connection con;
    private final HallMapper hallMapper;

    public HallDaoImpl(Connection con) {
        this.con = con;
        hallMapper = new HallMapper();
    }

    @Override
    public void add(Hall entity) {
        try (PreparedStatement ps = con.prepareStatement(HallConstants.ADD_HALL)) {
            int i = 1;
            ps.setString(i++, entity.getName());
            ps.setString(i, entity.getDescription());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            log.error("Cannot save hall" + e);
            throw new DBException("Invalid hall input", e);
        }
    }

    @Override
    public Hall findById(Long id) {
        try (PreparedStatement ps = con.prepareStatement(HallConstants.FIND_HALL_BY_ID)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return hallMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find hall" + e);
            throw new DBException("Cannot find hall", e);
        }
        return null;
    }

    @Override
    public List<Hall> findAll() {
        List<Hall> halls = new CopyOnWriteArrayList<>();

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(HallConstants.FIND_ALL_HALLS);

            while (rs.next()) {
                Hall result = hallMapper.extractFromResultSet(rs);
                halls.add(result);
            }
        } catch (SQLException e) {
            log.error("Cannot find halls" + e);
            throw new DBException("Exception finding all halls", e);
        }
        return halls;
    }

    @Override
    public void update(Hall entity) {
        try (PreparedStatement ps = con.prepareStatement(HallConstants.UPDATE_HALL)) {
            int i = 1;
            ps.setString(i++, entity.getName());
            ps.setString(i++, entity.getDescription());
            ps.setLong(i, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update hall" + e);
            throw new DBException("Cannot update hall", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement ps = con.prepareStatement(HallConstants.DELETE_HALL_BY_ID)) {
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete hall" + e);
            throw new DBException("Cannot delete hall", e);
        }
    }

    @Override
    public void deleteEntity(Hall entity) {
        delete(entity.getId());
    }

    @Override
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            log.error("Cannot close the connection" + e);
            throw new DBException("Cannot close the connection", e);
        }
    }

    @Override
    public Hall findByName(String name) {
        try (PreparedStatement ps = con.prepareStatement(HallConstants.FIND_HALL_BY_NAME)) {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return hallMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find hall by name" + e);
            throw new DBException("Cannot find hall", e);
        }
        return null;
    }

}
