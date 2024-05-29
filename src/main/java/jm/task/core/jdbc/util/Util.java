package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public Util(){}
    public SessionFactory getHibernateConnect() throws IOException, ClassNotFoundException {
        Properties connect = new Properties();
        connect.load(Files.newInputStream(
                Paths.get("./db.properties").normalize()));
        return new Configuration()
                .setProperties(connect)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public Connection getDBconnection () throws ClassNotFoundException, SQLException, IOException {
        Properties dataConnect = new Properties();
        dataConnect.load(Files.newInputStream(
                Paths.get("./db.properties").normalize()));
        Class.forName(dataConnect.getProperty("dbDriver"));
        return DriverManager.getConnection(
                dataConnect.getProperty("url"),
                dataConnect.getProperty("dbUsername"),
                dataConnect.getProperty("dbPassword"));
    }
}
