package com.dstu.application.entities;

import com.dstu.application.interfaces.SubjectAbstract;


public class Credit extends SubjectAbstract {

    public Credit(int id, String name, String surname, int numberOfCreditBook, String subjectName, int hours, String mark) {
        super(id, name, surname, numberOfCreditBook, subjectName, hours, mark);
    }
}
