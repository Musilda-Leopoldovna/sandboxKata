package jm.task.core.jdbc.util;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Properties;

public class Util {
    public static Connection getDBconnection () throws ClassNotFoundException, SQLException, IOException {
        Properties dataConnect = new Properties();
        InputStream in = Files.newInputStream(Paths.get("./db.properties").normalize());
        dataConnect.load(in);
        Class.forName(dataConnect.getProperty("dbDriver"));
        return DriverManager.getConnection(
                dataConnect.getProperty("url"),
                dataConnect.getProperty("dbUsername"),
                dataConnect.getProperty("dbPassword"));
    }
}
