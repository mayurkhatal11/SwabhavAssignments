package com.bankapp.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

    // 1. A private static instance of this class (the single instance)
    private static DBConnection instance;
    
    // 2. The DataSource object, which will be looked up only once
    private DataSource dataSource;

    /**
     * 3. A private constructor to prevent anyone else from creating an instance.
     * This is where the one-time JNDI lookup happens.
     */
    private DBConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            // This is your JNDI name from context.xml
            this.dataSource = (DataSource) envContext.lookup("jdbc/bank_db"); 
        } catch (NamingException e) {
            // This is a critical error, if it fails the app can't connect to the DB
            e.printStackTrace();
            throw new RuntimeException("Failed to lookup DataSource during initialization.", e);
        }
    }

    /**
     * 4. The public static method that everyone will use to get the single instance.
     * It's "synchronized" to be thread-safe.
     * @return The single instance of DBConnection.
     */
    private static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * 5. The public static method that your DAOs will call to get a connection.
     * It gets the singleton instance and then gets a connection from its DataSource.
     * @return A Connection object from the pool.
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return getInstance().dataSource.getConnection();
    }
}