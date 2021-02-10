package com.dstu.application.threads;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadRunnable implements Runnable{

    private String queryString = "";
    private Connection connection;

    public ReadRunnable(String queryString, Connection connection) {
        this.queryString = queryString;
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.println("Read thread is started...");
        try {
            readQuery(queryString,connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Read is done");
    }

    private void readQuery(String queryString, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Getting data from database...");
        while(true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String studentName = resultSet.getString(1);
            String studentSurename = resultSet.getString(2);
            int numberOfRecordBook = resultSet.getInt(3);
            String subjectName = resultSet.getString(4);
            int countOfHours = resultSet.getInt(5);
            String mark = resultSet.getString(6);
            System.out.println("studentName= "+studentName +" studentSurename= "+studentSurename +
                    " numberOfRecordBook= "+ numberOfRecordBook
                    +" subjectName= "+ subjectName+" countOfHours= "+ countOfHours+" mark= "+mark);
        }
    }
}
