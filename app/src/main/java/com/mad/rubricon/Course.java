package com.mad.rubricon;

public class Course {
    public  long id;
    public String courseId;
    public  String courseTitle;

    public  Course(){
        id = 0;
        courseId = "";
        courseTitle = "";
    }

    public  Course(long id, String crsid, String title){
        this.id = id;
        courseId = crsid;
        courseTitle = title;
    }

    public  long getId(){
        return  id;
    }

    public String getCourseId(){
        return courseId;
    }

    public  String getCourseTitle(){
        return courseTitle;
    }
}

