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
    private Rubric(){
        rubricCLOs = new ArrayList<>();
        gradingLevels = new ArrayList<>();
    }

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
        gradingLevels.add(gradingLevels.size(),level);
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
        rubricTable.createEntry(id,courseID,"Rubric",teacherID,section);
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

    public void saveClos(Context context){
        RubricCLOTable cloTable = new RubricCLOTable(context);
        cloTable.open();
        CriteriaTable criteriaTable = new CriteriaTable(context);
        criteriaTable.open();
        BridgeGCTable gcTable = new BridgeGCTable(context);
        gcTable.open();

        int cloId = cloTable.getCount();
        for (RubricCLO rubricCLO : rubricCLOs){
            rubricCLO.id = cloId;
            cloTable.createEntry(rubricCLO.id,rubricCLO.cloID,rubricCLO.rubricID);

            int criteriaId = criteriaTable.getCount();
            for(Criteria criteria : rubricCLO.criteriaArrayList){
                criteriaTable.createEntry(criteriaId,criteria.title,rubricCLO.id,criteria.description);

                int gcId = gcTable.getCount();
                for (BridgeGC bridge : criteria.bridgeGCs){
                    gcTable.createEntry(gcId,bridge.gradingLevelID,criteriaId,bridge.description);
                    gcId++;
                }
                criteriaId++;
            }
            cloId++;
        }
        cloTable.close();
        criteriaTable.close();
        gcTable.close();
        rubric = new Rubric();
    }
}
