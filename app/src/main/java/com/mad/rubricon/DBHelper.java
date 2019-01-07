package com.mad.rubricon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LabGrader";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS PersonTable  (PersonID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "Name VARCHAR NOT NULL,Email VARCHAR NOT NULL UNIQUE,CNIC VARCHAR NOT NULL UNIQUE,DOB VARCHAR NOT NULL,Password VARCHAR NOT NULL," +
                "Role VARCHAR NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS TeacherTable  (PersonID INTEGER PRIMARY KEY NOT NULL," +
                "Department VARCHAR NOT NULL,Qualification VARCHAR NOT NULL,Designation VARCHAR NOT NULL,  FOREIGN KEY('PersonID') REFERENCES `PersonTable`(`PersonID`) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS StudentTable  (PersonID INTEGER PRIMARY KEY NOT NULL," +
                "RegNo VARCHAR NOT NULL UNIQUE,Department VARCHAR NOT NULL, FOREIGN KEY('PersonID') REFERENCES `PersonTable`(`PersonID`) );");
        db.execSQL("CREATE TABLE IF NOT EXISTS CourseTable  (CourseCode VARCHAR PRIMARY KEY NOT NULL," +
                "CourseTitle VARCHAR NOT NULL UNIQUE, TheoryCredit INTEGER NOT NULL, LabCredit INTEGER Not NULL, KnowledgeArea VARCHAR NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS CourseCloTable  (CourseCode VARCHAR NOT NULL," +
                "CloNo INTEGER NOT NULL, CloPriority VARCHAR NOT NULL, Description VARCHAR Not NULL, PRIMARY KEY (CourseCode,CloNo),FOREIGN KEY('CourseCode') REFERENCES `CourseTable`(`CourseCode`));");
        db.execSQL("CREATE TABLE IF NOT EXISTS CourseReqTable  (CourseCode VARCHAR NOT NULL," +
                "PreReq VARCHAR NOT NULL,FOREIGN KEY('CourseCode') REFERENCES `CourseTable`(`CourseCode`));");
        db.execSQL("CREATE TABLE IF NOT EXISTS SectionTable  (SectionID VARCHAR NOT NULL," +
                "SessionID VARCHAR NOT NULL, CourseCode VARCHAR NOT NULL, SectionName VARCHAR Not NULL," +
                "PRIMARY KEY (SectionID,CourseCode),FOREIGN KEY('CourseCode') REFERENCES `CourseTable`(`CourseCode`));");
        db.execSQL("CREATE TABLE IF NOT EXISTS StudentCourseTable  (PersonID INT NOT NULL," +
                "CourseCode VARCHAR NOT NULL, SectionID VARCHAR NOT NULL,PRIMARY Key(PersonID,CourseCode),FOREIGN KEY('CourseCode') REFERENCES `CourseTable`(`CourseCode`)," +
                "FOREIGN KEY('SectionID') REFERENCES `SectionTable`(`SectionID`),FOREIGN KEY('PersonID') REFERENCES `PersonTable`(`PersonID`));");
        db.execSQL("CREATE TABLE IF NOT EXISTS TeacherCourseTable  (PersonID INT NOT NULL," +
                "CourseCode VARCHAR NOT NULL, SectionID VARCHAR NOT NULL,FOREIGN KEY('CourseCode') REFERENCES `CourseTable`(`CourseCode`)," +
                "FOREIGN KEY('SectionID') REFERENCES `SectionTable`(`SectionID`),FOREIGN KEY('PersonID') REFERENCES `PersonTable`(`PersonID`));");
        db.execSQL("CREATE TABLE IF NOT EXISTS SessionTable  (SessionID VARCHAR NOT NULL," +
                "CourseCode VARCHAR NOT NULL, Year VARCHAR Not NULL," +
                "PRIMARY KEY (SessionID,CourseCode),FOREIGN KEY('CourseCode') REFERENCES `CourseTable`(`CourseCode`));");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS persontable");
        db.execSQL("DROP TABLE IF EXISTS teachertable");
        db.execSQL("DROP TABLE IF EXISTS studenttable");
        db.execSQL("DROP TABLE IF EXISTS coursetable");
        db.execSQL("DROP TABLE IF EXISTS teachercoursetable");
        db.execSQL("DROP TABLE IF EXISTS studentcoursetable");
        db.execSQL("DROP TABLE IF EXISTS couresclotable");
        db.execSQL("DROP TABLE IF EXISTS coursereqtable");
        db.execSQL("DROP TABLE IF EXISTS sectiontable");
        db.execSQL("DROP TABLE IF EXISTS sessiontable");
        onCreate(db);
    }
}
