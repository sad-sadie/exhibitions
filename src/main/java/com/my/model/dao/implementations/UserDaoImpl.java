package com.my.model.dao.implementations;

import com.my.model.dao.constants.UserConstants;
import com.my.model.dao.UserDao;
import com.my.model.dao.mappers.UserMapper;
import com.my.model.dao.util.Md5Utils;
import com.my.model.entities.User;
import com.my.model.dao.exeptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDaoImpl implements UserDao {

    private static final Logger log = Logger.getLogger(UserDaoImpl.class);

    private final Connection con;
    private final UserMapper userMapper;

    public UserDaoImpl(Connection con) {
        this.con = con;
        userMapper = new UserMapper();
    }

    @Override
    public void add(User entity) {
        try (PreparedStatement ps = con.prepareStatement(UserConstants.ADD_USER)) {
            int i = 1;
            ps.setString(i++, entity.getEmail());
            ps.setString(i++, entity.getLogin());
            ps.setString(i++, entity.getPassword());
            ps.setInt(i, entity.getRole().ordinal());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            log.error("Cannot add user." + e);
            throw new DBException("Invalid user input", e);
        }
    }

    @Override
    public void setCorrectID(User user) {
        try(PreparedStatement ps = con.prepareStatement(UserConstants.GET_ID)) {
            ps.setString(1, user.getLogin());

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                user.setId(rs.getLong(UserConstants.FIELD_ID));
            }

        } catch (SQLException e) {
            log.error("Cannot set correct id" + e);
            throw new DBException("Cannot set correct id to new user", e);
        }
    }

    @Override
    public User findById(Long id) {
        try (PreparedStatement ps = con.prepareStatement(UserConstants.FIND_USER_BY_ID)) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return userMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find user by name." + e);
            throw new DBException("Cannot find user", e);
        }
        return null;
    }

    @Override
    public List<User> findAll(){
        List<User> users = new CopyOnWriteArrayList<>();

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(UserConstants.FIND_ALL_USERS);

            while (rs.next()) {
                User result = userMapper.extractFromResultSet(rs);
                users.add(result);
            }
        } catch (SQLException e) {
            log.error("Cannot find all users." + e);
            throw new DBException("Exception finding all users", e);
        }
        return users;
    }

    @Override
    public void update(User entity){
        entity.setPassword(Md5Utils.toMd5(entity.getPassword()));
        try (PreparedStatement ps = con.prepareStatement(UserConstants.UPDATE_USER)) {
            int i = 1;
            ps.setString(i++, entity.getEmail());
            ps.setString(i++, entity.getLogin());
            ps.setString(i++, entity.getPassword());
            ps.setInt(i++, entity.getRole().ordinal());
            ps.setLong(i, entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update user." + e);
            throw new DBException("Cannot update user", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (PreparedStatement ps = con.prepareStatement(UserConstants.DELETE_USER_BY_ID)) {
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete user." + e);
            throw new DBException("Cannot delete user", e);
        }
    }

    @Override
    public void deleteEntity(User entity){
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
    public User findByLoginAndPassword(String login, String password)  {
        password = Md5Utils.toMd5(password);
        try (PreparedStatement ps = con.prepareStatement(UserConstants.FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return userMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find user." + e);
            throw new DBException("Cannot find user", e);
        }
        return null;
    }

    @Override
    public User findByLogin(String login) {
        try (PreparedStatement ps = con.prepareStatement(UserConstants.FIND_USER_BY_LOGIN)) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return userMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error("Cannot find user." + e);
            throw new DBException("Cannot find user", e);
        }
        return null;
    }

    @Override
    public void buyTicket(long userID, long exhibitionID) {
        try(PreparedStatement ps = con.prepareStatement(UserConstants.SET_ORDERS)) {
            ps.setLong(1, exhibitionID);
            ps.setLong(2, userID);

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot submit the purchase." + e);
            throw new DBException("Cannot submit the order", e);
        }
    }
}
