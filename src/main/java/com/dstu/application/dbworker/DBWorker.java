package com.dstu.application.dbworker;

import com.dstu.application.csvworker.CSVWorker;
import com.dstu.application.entities.Credit;
import com.dstu.application.interfaces.Subject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBWorker {

    private DBConnector dbConnector = new DBConnector();
    private Connection connection = dbConnector.getConnection();
    private CSVWorker csvWorker;

    public DBWorker(String csvFile) throws SQLException, IOException {
        csvWorker = new CSVWorker(csvFile);
        ArrayList<Subject> records = csvWorker.getExams();
        Statement statement = connection.createStatement();
        createTables(statement);
        writeToDatabase(records);
        executeCustomQuery();
//        connection.close();
//        System.out.println("Connection is down...");
    }

    private void createTables(Statement statement) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS student(id integer primary key auto_increment, " +
                "studentName varchar(100), studentSurename varchar(100), numberOfRecordBook varchar(10));");
        statement.execute("CREATE TABLE IF NOT EXISTS exam(id integer primary key auto_increment, " +
                "subjectName varchar(100), countOfHours integer, mark varchar(10), student_id integer, " +
                " FOREIGN KEY (student_id)  REFERENCES student (id));");
        statement.execute("CREATE TABLE IF NOT EXISTS credit(id integer primary key auto_increment, " +
                "subjectName varchar(100), countOfHours integer, mark varchar(10), student_id integer, " +
                " FOREIGN KEY (student_id)  REFERENCES student (id));");
    }

    private void writeToDatabase(ArrayList<Subject> arrayList) throws SQLException {
        writeStudents(arrayList);
        writeSubjects(arrayList);
    }

    private void writeStudents(ArrayList<Subject> arrayList) throws SQLException {
        for (Subject s: arrayList) {
            if(!checkStudentInTable(s)){
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement("INSERT INTO student VALUES (null, ?,?,?); ");
                setStudentParam(preparedStatement, s);
                preparedStatement.executeUpdate();
            }
        }
    }

    private void writeSubjects(ArrayList<Subject> arrayList) throws SQLException {
        for(Subject s:arrayList){
            PreparedStatement preparedStatement = null;
            if(s instanceof Credit){
                if(!checkCreditInTable(s)){
                    preparedStatement = connection.prepareStatement("INSERT INTO credit VALUES (null, ?, ?, ?,?); ");
                }
            }else{
                if(!checkExamInTable(s)){
                    preparedStatement = connection.prepareStatement("INSERT INTO exam VALUES (null, ?, ?, ?,?); ");
                }
            }
            if(preparedStatement != null){
                setExamsParam(preparedStatement,s);
                preparedStatement.executeUpdate();
            }
        }
    }

    private boolean checkStudentInTable(Subject s) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT * FROM student " +
                "WHERE studentName=? AND studentSurename=? AND numberOfRecordBook =?");
        setStudentParam(preparedStatement, s);
        return preparedStatement.executeQuery().next();
    }

    private boolean checkExamInTable(Subject s) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT * FROM exam WHERE" +
                " subjectName =? AND countOfHours =? AND mark =? AND student_id =?");
        setExamsParam(preparedStatement,s);
        return preparedStatement.executeQuery().next();
    }

    private boolean checkCreditInTable(Subject s) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT * FROM credit WHERE" +
                " subjectName =? AND countOfHours =? AND mark =? AND student_id =?");
        setExamsParam(preparedStatement,s);
        return preparedStatement.executeQuery().next();
    }

    private PreparedStatement setStudentParam(PreparedStatement preparedStatement, Subject s) throws SQLException {
        preparedStatement.setString(1,s.getName());
        preparedStatement.setString(2,s.getSurname());
        preparedStatement.setString(3,String.valueOf(s.getNumberOfCreditBook()));
        return preparedStatement;
    }

    private PreparedStatement setExamsParam(PreparedStatement preparedStatement, Subject s) throws SQLException {
        preparedStatement.setString(1, s.getSubjectName());
        preparedStatement.setString(2, String.valueOf(s.getHours()));
        preparedStatement.setString(3, s.getMark());
        preparedStatement.setString(4, String.valueOf(getStudentId(s.getNumberOfCreditBook())));
        return preparedStatement;
    }

    private int getStudentId(int recordBook) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student WHERE" +
                " numberOfRecordBook =?");
        preparedStatement.setString(1,String.valueOf(recordBook));
        ResultSet resultSet = preparedStatement.executeQuery();
        int id = 0;
        if(resultSet.next()){
            id = resultSet.getInt("id");
        }
        return id;
    }

    public void executeCustomQuery() throws SQLException {
        String s = "UPDATE student SET studentName = 'Alexandr' WHERE id=5";
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate("DELETE FROM exam WHERE student_id =1");
    }

    public void getAllFromDB() throws SQLException {
        String s = "SELECT studentName, studentSurename, numberOfRecordBook, subjectName, countOfHours, mark" +
                " FROM exam, student where exam.student_id=student.id UNION " +
                "SELECT studentName, studentSurename, numberOfRecordBook, subjectName, countOfHours, mark" +
                " FROM credit, student where credit.student_id=student.id order by numberOfRecordBook";
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Getting data from database...");
        while(resultSet.next()){
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
        System.out.println("Done!");
    }
}
