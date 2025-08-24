package com.bankapp.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

    private static DBConnection instance;
    
    private DataSource dataSource;

    private DBConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            this.dataSource = (DataSource) envContext.lookup("jdbc/bank_db"); 
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to lookup DataSource during initialization.", e);
        }
    }

    private static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().dataSource.getConnection();
    }
}
