package com.mad.rubricon;

import java.util.ArrayList;

public class RubricCLO {
//   cloID type depend on the 1st group
    public static RubricCLO rubricCLO = new RubricCLO();
    public int id;
    public int cloID;
    public int rubricID;
    public ArrayList<Criteria> criteriaArrayList = new ArrayList<>();

    public RubricCLO(int cloID, int rubricID) {
        this.cloID = cloID;
        this.rubricID = rubricID;
    }
    public RubricCLO(){}
    public static void refresh(){
        rubricCLO = new RubricCLO();
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
        criteriaArrayList.add(criteriaArrayList.size(),criteria);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
