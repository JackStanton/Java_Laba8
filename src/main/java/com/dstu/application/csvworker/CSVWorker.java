package com.dstu.application.csvworker;

import com.dstu.application.entities.Credit;
import com.dstu.application.entities.Exam;
import com.dstu.application.interfaces.Subject;

import java.io.*;
import java.util.ArrayList;

public class CSVWorker implements Serializable{
    private String fileName;
    private ArrayList<Subject> exams;

    public CSVWorker(String fileName) throws IOException {
        this.fileName = fileName;
        this.exams = getObjectsList(fileName);
    }

    public CSVWorker() {
    }

    public ArrayList<Subject> getExams() {
        return exams;
    }

    private ArrayList<Subject> getObjectsList(String fileName) throws IOException {
        String del = ";";
        String[] splitString;
        ArrayList<Subject> ret = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                splitString = line.replaceAll(String.valueOf((char) 65279),"").split(del);
                if (checkInteger(splitString[0])&&checkInteger(splitString[4])){
                    Subject subject;
                    if (splitString[5].equals("зач")){
                        subject = new Credit(Integer.parseInt(splitString[0]), splitString[1], splitString[2],Integer.parseInt(splitString[6]), splitString[3], Integer.parseInt(splitString[4]), splitString[5]);
                    }else {
                        subject = new Exam(Integer.parseInt(splitString[0]), splitString[1], splitString[2],Integer.parseInt(splitString[6]), splitString[3], Integer.parseInt(splitString[4]), splitString[5]);
                    }
                    ret.add(subject);
                }
                line = reader.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private boolean checkInteger(String str){
        boolean res = false;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if(!(ch[i] >= 48 && ch[i] <= 59)){
                return false;
            }
        }
        return true;
    }
}
