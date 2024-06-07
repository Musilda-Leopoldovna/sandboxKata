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
import java.util.logging.Logger;

public final class Util {
    private static final Util CONNECT = new Util();
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static SessionFactory SESSION_FACTORY = null;
    private static Connection CONNECTION = null;

    private Util() {
    }

    private static Util getUtil() {
        return CONNECT;
    }

    public static Connection getCONNECTION() {
        if (CONNECTION == null) {
            CONNECTION = getUtil().getJDBCconnection();
        }
        return CONNECTION;
    }

    public static SessionFactory getSessionFactory() {
        if (SESSION_FACTORY == null) {
            SESSION_FACTORY = getUtil().getHibernateConnection();
        }
        return SESSION_FACTORY;
    }

    private SessionFactory getHibernateConnection() {
        Properties connect = new Properties();
        try {
            connect.load(Files.newInputStream(
                    Paths.get("./db.properties").normalize()));
        } catch (IOException e) {
            LOGGER.warning("Connection settings are invalid.");
            throw new RuntimeException(e);
        }
        return new Configuration()
                .setProperties(connect)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    private Connection getJDBCconnection () {
        Properties newConnect = new Properties();
        Connection connection;
        try {
            newConnect.load(Files.newInputStream(
                    Paths.get("./db.properties").normalize()));
            Class.forName(newConnect.getProperty("dbDriver"));
            connection = DriverManager.getConnection(
                    newConnect.getProperty("url"),
                    newConnect.getProperty("dbUsername"),
                    newConnect.getProperty("dbPassword"));
        } catch (ClassNotFoundException | SQLException | IOException e) {
            LOGGER.warning("Connection settings are invalid.");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
