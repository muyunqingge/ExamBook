package com.example.exambooktest.mysql;

public class Score {
    private int id;
    private String studentId;
    private double score;
    private String date;
    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", score=" + score +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
