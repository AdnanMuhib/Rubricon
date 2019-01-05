package com.mad.rubricon;

import java.util.ArrayList;


public class Rubric {
    public static Rubric rubric = new Rubric();
    String courseID;
    String teacherID;
    String rubricTitle;
    String section;
    ArrayList<RubricCLO> rubricCLOs = new ArrayList<>();
    ArrayList<GradingLevel> gradingLevels = new ArrayList<>();


    private Rubric(String courseID, String rubricTitle, String teacherID, String section) {
        this.courseID = courseID;
        this.rubricTitle = rubricTitle;
        this.teacherID = teacherID;
        this.section = section;
    }
    private Rubric(){}

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getRubricTitle() {
        return rubricTitle;
    }

    public void setRubricTitle(String rubricTitle) {
        this.rubricTitle = rubricTitle;
    }

    public void addRubricClo(RubricCLO rubricCLO){
        rubricCLOs.add(rubricCLO);
    }

    public void addGradingLevel(GradingLevel level){
        gradingLevels.add(level);
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
