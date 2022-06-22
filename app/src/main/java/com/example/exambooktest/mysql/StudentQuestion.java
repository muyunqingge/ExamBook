package com.example.exambooktest.mysql;

import androidx.annotation.NonNull;

public class StudentQuestion {

    private int id;                 //主键id
    private String student_id;      //学生学号
    private int question_id;        //题号
    private int question_right;     //没做过为0，做对了为1，做错了为2

    @Override
    public String toString() {
        return "StudentQuestion{" +
                "id=" + id +
                ", student_id='" + student_id + '\'' +
                ", question_id=" + question_id +
                ", question_right=" + question_right +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuestion_right() {
        return question_right;
    }

    public void setQuestion_right(int question_right) {
        this.question_right = question_right;
    }
}
