package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String userData = "CREATE TABLE IF NOT EXISTS user " + "(ID BIGINT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, " +
                "UNIQUE INDEX ID (name, lastname, age))";
        try(Connection newConnection = util.getDBconnection();
            Statement st = newConnection.createStatement())
        {
            st.execute(userData);

        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try(Connection newConnection = util.getDBconnection();
            Statement st = newConnection.createStatement())
        {
            st.execute(sql);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT IGNORE INTO user (name, lastname, age) VALUES (?, ?, ?)";
        String addedUser = String.format("User name '%s %s' added in DB.", name, lastName);
        try(Connection newConnection = util.getDBconnection();
            PreparedStatement prSt = newConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setByte(3, age);
            prSt.executeUpdate();
            LOGGER.info(addedUser);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE ID = ?";
        try(Connection newConnection = util.getDBconnection();
            PreparedStatement prpSt = newConnection.prepareStatement(sql))
        {
            prpSt.setLong(1, id);
            prpSt.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try(Connection newConnection = util.getDBconnection();
            Statement st = newConnection.createStatement())
        {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                userList.add(user);
            }
            return userList;

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM user";
        try(Connection newConnection = util.getDBconnection();
            Statement st = newConnection.createStatement())
        {
            st.executeUpdate(sql);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
