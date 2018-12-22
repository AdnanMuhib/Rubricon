package com.mad.rubricon;

import java.util.ArrayList;

public class RubricCLO {
//   cloID type depend on the 1st group
    private int cloID;
    private int rubricID;
    private ArrayList<Criteria> criteriaArrayList = new ArrayList<>();

    public RubricCLO(int cloID, int rubricID) {
        this.cloID = cloID;
        this.rubricID = rubricID;
    }

    public int getCloID() {
        return cloID;
    }

    public void setCloID(int cloID) {
        this.cloID = cloID;
    }

    public int getRubricID() {
        return rubricID;
    }

    public void setRubricID(int rubricID) {
        this.rubricID = rubricID;
    }

    public void addCriteria(Criteria criteria){
        criteriaArrayList.add(criteria);
    }
}
