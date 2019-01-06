package com.mad.rubricon;

import android.content.Context;

import java.util.ArrayList;


public class Rubric {
    public static Rubric rubric = new Rubric();
    int id;
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

    public void saveRubric(Context context){
        // Saving the rubric to DB...
        RubricTable rubricTable = new RubricTable(context);
        rubricTable.open();
        this.id = rubricTable.getCount();
        rubricTable.createEntry(id,courseID,rubricTitle,teacherID,section);
        rubricTable.close();

        // Saving grading level of the rubric to the DB...
        GradingLevelTable table = new GradingLevelTable(context);
        table.open();
        int id = table.getCount();
        for (GradingLevel level: gradingLevels) {
            level.setRubricID(this.id);
            level.id = id;
            table.createEntry(level.id,level.title,level.rubricID,level.marks);
            id++;
        }
        table.close();
    }
}
