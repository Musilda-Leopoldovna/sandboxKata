package kata.pp.jdbc.dao;

import kata.pp.jdbc.model.User;
import kata.pp.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String userData = "CREATE TABLE IF NOT EXISTS user " + "(ID BIGINT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, " +
                "UNIQUE INDEX ID (name, lastname, age))";
        try(Connection connection = Util.getJDBCconnection(); Statement st = connection.createStatement()) {
            st.execute(userData);
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try(Connection connection = Util.getJDBCconnection(); Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT IGNORE INTO user (name, lastname, age) VALUES (?, ?, ?)";
        try(Connection connection = Util.getJDBCconnection(); PreparedStatement prSt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
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
        try(Connection connection = Util.getJDBCconnection(); PreparedStatement prpSt = connection.prepareStatement(sql)) {
            prpSt.setLong(1, id);
            prpSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY ID";
        try(Connection con = Util.getJDBCconnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

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
        try(Connection connection = Util.getJDBCconnection(); Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
