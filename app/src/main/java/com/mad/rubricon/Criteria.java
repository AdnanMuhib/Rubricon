package com.mad.rubricon;

import java.util.ArrayList;

public class Criteria {
    public static Criteria criteria = new Criteria();
    String title;
    String description;
    int rubricCloID;
    ArrayList<BridgeGC> bridgeGCs = new ArrayList<>();

    public Criteria(String title, String description, int rubricCloID) {
        this.title = title;
        this.description = description;
        this.rubricCloID = rubricCloID;
    }
    private Criteria(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRubricCloID() {
        return rubricCloID;
    }

    public void setRubricCloID(int rubricCloID) {
        this.rubricCloID = rubricCloID;
    }

    public void addBridgeGC(BridgeGC bridgeGC){
        bridgeGCs.add(bridgeGC);
    }
}
