package com.dstu.application;

import com.dstu.application.dbworker.DBWorker;

import java.io.IOException;
import java.sql.*;

public class ApplicationStarter {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DBWorker dbWorker = new DBWorker(args[0]);
        dbWorker.getAllFromDB();
    }
}
