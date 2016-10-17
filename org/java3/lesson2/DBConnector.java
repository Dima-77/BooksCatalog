package org.java3.lesson2;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

/**
 * Created by Dima on 07.10.2016.
 */
public class DBConnector {
    private final String URL_CONNECTION = "jdbc:sqlite:C:/test/lesson2.db1";
    private static DBConnector instance;
    private static Connection connection;
    private static Logger LOG;
    private DBConnector () throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(URL_CONNECTION);
        LOG = LoggerFactory.getLogger(DBConnector.class);
        LOG.info("Соединение с базой данных создано");
    }

    public static DBConnector getInstance () {
        if (instance == null) {
            try {
                instance = new DBConnector();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static Connection getConnection () {
        if (instance == null) {
            getInstance();
        }
        return connection;
    }
}
