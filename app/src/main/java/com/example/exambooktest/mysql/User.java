package com.example.exambooktest.mysql;

import androidx.annotation.NonNull;

public class User {
    private String studentId;       //学号
    private String name;            //姓名
    private String password;        //密码
    private String phone;           //手机号
    private String gender;          //性别
    private String school;          //学校
    private String college;         //学院
    private String major;           //专业
    private String grade;           //班级
    private String netName;         //网名
    private Float mockScore;       //模拟考试分数
    private Float formalScore;     //正式考试分数

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public Float getMockScore() {
        return mockScore;
    }

    public void setMockScore(Float mockScore) {
        this.mockScore = mockScore;
    }

    public Float getFormalScore() {
        return formalScore;
    }

    public void setFormalScore(Float formalScore) {
        this.formalScore = formalScore;
    }

    @Override
    public String toString() {
        return "User{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", school='" + school + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                ", netName='" + netName + '\'' +
                ", mockScore=" + mockScore +
                ", formalScore=" + formalScore +
                '}';
    }
}
