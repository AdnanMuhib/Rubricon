package com.mad.rubricon;

public class Question {
    double marks;
    int rubric_id;
    int lab_id;

    public Question(double marks, int rubric_id, int lab_id) {
        this.marks = marks;
        this.rubric_id = rubric_id;
        this.lab_id = lab_id;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public int getRubric_id() {
        return rubric_id;
    }

    public void setRubric_id(int rubric_id) {
        this.rubric_id = rubric_id;
    }

    public int getLab_id() {
        return lab_id;
    }

    public void setLab_id(int lab_id) {
        this.lab_id = lab_id;
    }
}
