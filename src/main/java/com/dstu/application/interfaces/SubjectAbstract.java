package com.dstu.application.interfaces;

import java.io.Serializable;

public class SubjectAbstract implements Subject, Serializable {

    private int id;
    private String name;
    private String surname;
    private int numberOfCreditBook;
    private String subjectName;
    private int hours;
    private String mark;

    public SubjectAbstract(int id, String name, String surname, int numberOfCreditBook, String subjectName, int hours, String mark) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.numberOfCreditBook = numberOfCreditBook;
        this.subjectName = subjectName;
        this.hours = hours;
        this.mark = mark;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public int getHours() {
        return hours;
    }

    @Override
    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public int getNumberOfCreditBook() {
        return numberOfCreditBook;
    }

    @Override
    public void setNumberOfCreditBook(int numberOfCreditBook) {
        this.numberOfCreditBook = numberOfCreditBook;
    }

    @Override
    public String toString() {
        return "SubjectAbstract{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", hours=" + hours +
                ", mark='" + mark + '\'' +
                ", numberOfCreditBook=" + numberOfCreditBook +
                '}';
    }
}
