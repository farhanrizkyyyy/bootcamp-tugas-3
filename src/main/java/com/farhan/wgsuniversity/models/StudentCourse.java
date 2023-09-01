package com.farhan.wgsuniversity.models;

public class StudentCourse {
    private String id;
    private String courseId;
    private String studentId;
    private Byte quiz1;
    private Byte quiz2;
    private Byte quiz3;
    private Byte quiz4;
    private Byte quiz5;
    private Byte test1;
    private Byte test2;

    public StudentCourse(String id, String courseId, String studentId) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
//        this.quiz1 = null;
//        this.quiz2 = null;
//        this.quiz3 = null;
//        this.quiz4 = null;
//        this.quiz5 = null;
//        this.test1 = null;
//        this.test2 = null;
    }

    public void setQuiz1(Byte score) {
        this.quiz1 = score;
    }

    public void setQuiz2(Byte score) {
        this.quiz2 = score;
    }

    public void setQuiz3(Byte score) {
        this.quiz3 = score;
    }

    public void setQuiz4(Byte score) {
        this.quiz4 = score;
    }

    public void setQuiz5(Byte score) {
        this.quiz5 = score;
    }

    public void setTest1(Byte score) {
        this.test1 = score;
    }

    public void setTest2(Byte score) {
        this.test2 = score;
    }
}
