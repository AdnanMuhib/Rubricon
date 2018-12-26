package com.mad.rubricon;

import java.util.ArrayList;

public class Lab {
    String labTitle;
    double labMarks;
    double marksWeight;

    ArrayList<Question> questions = new ArrayList<>();

    public Lab(double labMarks, double marksWeight, String title) {
        this.labTitle = title;
        this.labMarks = labMarks;
        this.marksWeight = marksWeight;
    }

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
}
