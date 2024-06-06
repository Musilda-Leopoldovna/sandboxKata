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
    private Util() {
    }

    private static Util getUtil() {
        return CONNECT;
    }

    private static class GetConnection {
        private static final Connection CONNECTION = getUtil().getJDBCconnection();
    }
    public static Connection getCONNECTION() {
        return GetConnection.CONNECTION;
    }

    private static class GetSessionFactory {
        private static final SessionFactory SESSION_FACTORY = getUtil().getHibernateConnection();
    }
    public static SessionFactory getSessionFactory() {
        return GetSessionFactory.SESSION_FACTORY;
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
