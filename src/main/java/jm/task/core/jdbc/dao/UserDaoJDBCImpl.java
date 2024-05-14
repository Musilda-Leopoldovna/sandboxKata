package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String userData = "CREATE TABLE IF NOT EXISTS user " + "(ID BIGINT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, " +
                "UNIQUE INDEX ID (name, lastname, age))";
        try(Connection newConnection = util.getDBconnection(); Statement st = newConnection.createStatement()) {
            st.executeUpdate(userData);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public void dropUsersTable() {
        try(Connection newConnection = util.getDBconnection(); Statement st = newConnection.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection newConnection = util.getDBconnection();
            PreparedStatement prSt = newConnection.prepareStatement(
                    "INSERT IGNORE INTO user (name, lastname, age) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS))
        {

            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setByte(3, age);
            prSt.executeUpdate();

            Statement st = newConnection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user WHERE ID = (SELECT MAX(ID) FROM user)");
            rs.next();
                if (name.equals(rs.getString("name"))
                        && lastName.equals(rs.getString("lastname"))
                        && age == rs.getByte("age")
                ) {
                    LOGGER.log(Level.INFO, "User name '" + name + " " + lastName + "' added in DB.");
                }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public void removeUserById(long id) {
        try(Connection newConnection = util.getDBconnection();
            PreparedStatement prpSt = newConnection.prepareStatement("DELETE FROM user WHERE ID = ?")) {
            prpSt.setLong(1, id);
            prpSt.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Connection newConnection = util.getDBconnection(); Statement st = newConnection.createStatement()){
            ResultSet rs = st.executeQuery("SELECT * FROM user");
            while (rs.next()) {
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

    public void cleanUsersTable() {
        try(Connection newConnection = util.getDBconnection(); Statement st = newConnection.createStatement()) {
            st.executeUpdate("DELETE FROM user");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
