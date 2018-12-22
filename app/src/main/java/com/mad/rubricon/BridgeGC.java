package com.mad.rubricon;

public class BridgeGC {
    int gradingLevelID;
    int criteriaID;
    String description;


    public BridgeGC(int gradingLevelID, int criteriaID, String description) {
        this.gradingLevelID = gradingLevelID;
        this.criteriaID = criteriaID;
        this.description = description;
    }

    public int getGradingLevelID() {
        return gradingLevelID;
    }

    public void setGradingLevelID(int gradingLevelID) {
        this.gradingLevelID = gradingLevelID;
    }

    public int getCriteriaID() {
        return criteriaID;
    }

    public void setCriteriaID(int criteriaID) {
        this.criteriaID = criteriaID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
