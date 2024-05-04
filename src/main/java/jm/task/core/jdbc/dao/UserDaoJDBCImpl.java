package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String userData = "CREATE TABLE IF NOT EXISTS user " + "(ID BIGINT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, " +
                "UNIQUE INDEX ID (name, lastname, age))";
        try(Connection newConnection = Util.getDBconnection(); Statement st = newConnection.createStatement()) {
            st.executeUpdate(userData);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public void dropUsersTable() {
        String userData = "DROP TABLE IF EXISTS user";
        try(Connection newConnection = Util.getDBconnection(); Statement st = newConnection.createStatement()) {
            st.executeUpdate(userData);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String userData = "INSERT IGNORE INTO user (name, lastname, age) VALUES (?, ?, ?) ";
        try(Connection newConnection = Util.getDBconnection(); PreparedStatement prSt = newConnection.prepareStatement(userData, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setByte(3, age);
            prSt.executeUpdate(); // ""
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public void removeUserById(long id) {
        String userData = "DELETE FROM user WHERE ID = ?";
        try(Connection newConnection = Util.getDBconnection(); PreparedStatement prpSt = newConnection.prepareStatement(userData)) {
            prpSt.setLong(1, id);
            prpSt.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    public List<User> getAllUsers() {
        String userData = "SELECT * FROM user";
        List<User> userList = new ArrayList<>();
        try(Connection newConnection = Util.getDBconnection(); Statement st = newConnection.createStatement()){
            ResultSet rs = st.executeQuery(userData);
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
        String userData = "DELETE FROM user";
        try(Connection newConnection = Util.getDBconnection(); Statement st = newConnection.createStatement()) {
            st.executeUpdate(userData);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
