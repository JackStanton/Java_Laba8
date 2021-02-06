package com.dstu.application.dbworker;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    private String userName = "user";
    private String password = "user";
    private String url = "jdbc:mysql://localhost/example";
    private Connection connection;

    public DBConnector(){
        try{
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection successful...");
        }catch (Exception e){
            System.out.println("Connection failed...");
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
