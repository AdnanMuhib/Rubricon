package com.mad.rubricon;

public class CourseData {
    private String Code;
    private String Name;
    private String Section;

    public CourseData(String code,String name, String sec){
        Code = code;
        Name = name;
        Section = sec;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String sec) {
        Section = sec;
    }
}

