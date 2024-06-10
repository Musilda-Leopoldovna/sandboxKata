package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String userData = "CREATE TABLE IF NOT EXISTS user " + "(ID BIGINT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, " +
                "UNIQUE INDEX ID (name, lastname, age))";
        try(Statement st = Util.getCONNECTION().createStatement()) {
            st.execute(userData);
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try(Statement st = Util.getCONNECTION().createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT IGNORE INTO user (name, lastname, age) VALUES (?, ?, ?)";
        try(PreparedStatement prSt = Util.getCONNECTION().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setByte(3, age);
            prSt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE ID = ?";
        try(PreparedStatement prpSt = Util.getCONNECTION().prepareStatement(sql)) {
            prpSt.setLong(1, id);
            prpSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try(Statement st = Util.getCONNECTION().createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                userList.add(user);
            }
            return userList;

        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM user";
        try(Statement st = Util.getCONNECTION().createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
