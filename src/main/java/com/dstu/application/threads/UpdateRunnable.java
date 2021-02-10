package com.dstu.application.threads;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRunnable implements Runnable{

    private final Connection connection;

    public UpdateRunnable(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.println("Update thread is started...");
        try {
            updateQuery(createQuery(), connection);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Update is done");
    }

    private void updateQuery(String queryString, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        preparedStatement.executeUpdate();
    }

    private String createQuery(){
        StringBuilder stringBuilder = new StringBuilder("UPDATE student SET studentName = 'Jack' WHERE");
        for (int i = 0; i < 100; i++) {
            stringBuilder.append(" id = '").append(i * 5 + 1).append("' OR ");
        }
        stringBuilder.delete(stringBuilder.length()-4,stringBuilder.length());
        stringBuilder.append(";");
        return stringBuilder.toString();
    }
}
