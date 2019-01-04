package com.mad.rubricon;

public class CourseMarks {
    private int id;
    private int studentId;
    private  String studentRegistration;
    private int courseId;
    private int weekId;
    private int questionId;
    private float marks;

    public CourseMarks(int i, String regNumber, float m){
        id = i;
        studentRegistration = regNumber;
        marks = m;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentRegistration() {
        return studentRegistration;
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration = studentRegistration;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getWeekId() {
        return weekId;
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public float getMarks() {
        return marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }
}
