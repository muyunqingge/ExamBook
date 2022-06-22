package com.example.exambooktest.mysql;

public class QuestionBank {
    private int id;
    private int questionChapter;
    private int questionType;
    private String title;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private int questionAnswer;
    private String questionAnalysis;
    private int subjectId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionChapter() {
        return questionChapter;
    }

    public void setQuestionChapter(int questionChapter) {
        this.questionChapter = questionChapter;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public int getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(int questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionAnalysis() {
        return questionAnalysis;
    }

    public void setQuestionAnalysis(String questionAnalysis) {
        this.questionAnalysis = questionAnalysis;
    }


    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }


}
