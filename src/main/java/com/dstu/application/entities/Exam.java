package com.dstu.application.entities;

import com.dstu.application.interfaces.SubjectAbstract;


public class Exam extends SubjectAbstract {
    public Exam(int id, String name, String surname, int numberOfCreditBook, String subjectName, int hours, String mark) {
        super(id, name, surname, numberOfCreditBook, subjectName, hours, mark);
    }
}
