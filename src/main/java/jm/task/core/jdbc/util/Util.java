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

public final class Util {
    private static final Util CONNECT = new Util();
    private static Connection CONNECTION;
    private static SessionFactory SESSION_FACTORY;
    private Util() {
    }

    private static Util getUtil() {
        return CONNECT;
    }

    public static Connection getCONNECTION() throws SQLException, IOException, ClassNotFoundException {
        if (CONNECTION == null) {
            CONNECTION = getUtil().getJDBCconnection();
        }
        return CONNECTION;
    }

    public static SessionFactory getSessionFactory() throws IOException, ClassNotFoundException {
        if (SESSION_FACTORY == null) {
            SESSION_FACTORY = getUtil().getHibernateConnection();
        }
        return SESSION_FACTORY;
    }

    private SessionFactory getHibernateConnection() throws IOException, ClassNotFoundException {
        Properties connect = new Properties();
        connect.load(Files.newInputStream(
                Paths.get("./db.properties").normalize()));
        return new Configuration()
                .setProperties(connect)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    private Connection getJDBCconnection () throws ClassNotFoundException, SQLException, IOException {
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
