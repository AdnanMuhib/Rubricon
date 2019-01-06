package com.mad.rubricon;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class Lab {
    public static Lab lab = new Lab();
    String labTitle;
    double labMarks;
    double marksWeight;
    String teacherID;
    String courseID;
    String SectionID;

    ArrayList<Question> questions = new ArrayList<>();

    public Lab(double labMarks, double marksWeight, String title) {
        this.labTitle = title;
        this.labMarks = labMarks;
        this.marksWeight = marksWeight;
    }
    public Lab(){}

    public double getLabMarks() {
        return labMarks;
    }

    public void setLabMarks(double labMarks) {
        this.labMarks = labMarks;
    }

    public double getMarksWeight() {
        return marksWeight;
    }

    public void setMarksWeight(double marksWeight) {
        this.marksWeight = marksWeight;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public String getLabTitle() {
        return labTitle;
    }

    public void setLabTitle(String labTitle) {
        this.labTitle = labTitle;
    }

    public void save(Context context){
        LabTable table = new LabTable(context);
        table.open();
        int labId = table.getCount();
        table.createEntry(labId,labMarks,marksWeight,labTitle,"2","2","D");

        Toast.makeText(context,table.getData(),Toast.LENGTH_SHORT).show();
        table.close();

        QuestionTable questionTable = new QuestionTable(context);
        questionTable.open();
        int qId = questionTable.getCount();
        for(Question question: questions){
            questionTable.createEntry(qId,question.marks,question.rubric_clo_id,question.lab_id);
            qId++;
        }
        questionTable.close();


    }
}
