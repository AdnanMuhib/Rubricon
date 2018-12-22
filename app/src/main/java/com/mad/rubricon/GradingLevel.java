package com.mad.rubricon;

public class GradingLevel {
    String title;
    double marks;
    int rubricID;

    public GradingLevel(String title, double marks, int rubricID) {
        this.title = title;
        this.marks = marks;
        this.rubricID = rubricID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public int getRubricID() {
        return rubricID;
    }

    public void setRubricID(int rubricID) {
        this.rubricID = rubricID;
    }
}
