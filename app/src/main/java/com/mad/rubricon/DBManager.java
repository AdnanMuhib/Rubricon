package com.mad.rubricon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {
    private com.mad.rubricon.DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;


    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new com.mad.rubricon.DBHelper(context);
        database = dbHelper.getWritableDatabase();

        return this;
    }

    public void close()
    {
        dbHelper.close();
        database.close();
    }

    public long registerTeacher(String Name,String Email,String CNIC,String DOB, String Password, String Designation,String Department, String Qualification)
    {
        String Role = "Teacher";
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Email",Email);
        contentValues.put("CNIC",CNIC);
        contentValues.put("DOB",DOB);
        contentValues.put("Password",Password);
        contentValues.put("Role",Role);
        long result = database.insert("PersonTable",null ,contentValues);
        ContentValues con = new ContentValues();

        Cursor c = database.query("PersonTable",new String[]{"PersonID","NAME","Email","CNIC","DOB","Password","Role"},"CNIC = ?", new String[]{CNIC},null,null,null);
        if(c.moveToFirst()){
            con.put("PersonID", c.getString(0));
        }
        con.put("Department",Department);
        con.put("Qualification",Qualification);
        con.put("Designation",Designation);

        long query2 = database.insert("TeacherTable",null,con);
        return query2;
    }

    public boolean teacherLogin(String email, String Password)
    {
        String Role = "Teacher";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("Select * from PersonTable where Email= ? and Password = ? and Role = ?",new String[]{email,Password,Role});
        if(c.getCount()>0) return true;
        return false;
    }

    public long registerStudent(String Name,String Email,String CNIC,String DOB, String Password, String RegNo)
    {
        String Role = "Student";
        String [] dep = RegNo.split("-");
        String dept = dep[1];
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Email",Email);
        contentValues.put("CNIC",CNIC);
        contentValues.put("DOB",DOB);
        contentValues.put("Password",Password);
        contentValues.put("Role",Role);
        long result = database.insert("PersonTable",null ,contentValues);
        ContentValues con = new ContentValues();

        Cursor c = database.query("PersonTable",new String[]{"PersonID","NAME","Email","CNIC","DOB","Password","Role"},"CNIC = ?", new String[]{CNIC},null,null,null);
        if(c.moveToFirst()){
            con.put("PersonID", c.getString(0));
        }
        con.put("RegNo",RegNo);
        con.put("Department",dept);
        long query2 = database.insert("StudentTable",null,con);
        return query2;
    }
    public boolean studentLogin(String RegNo, String Password)
    {
        String Role = "Student";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("Select * from StudentTable join PersonTable where RegNo = ? and Password = ? and Role = ?",new String[]{RegNo,Password,Role});
        if(c.getCount()>0) return true;
        return false;
    }
    public long registerAdmin(String Name,String Email,String CNIC,String DOB, String Password)
    {
        String Role = "Admin";
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Email",Email);
        contentValues.put("CNIC",CNIC);
        contentValues.put("DOB",DOB);
        contentValues.put("Password",Password);
        contentValues.put("Role",Role);
        long result = database.insert("PersonTable",null ,contentValues);
        return result;
    }
    public boolean adminLogin(String Email, String Password)
    {
        String Role = "Admin";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("Select * from PersonTable where Email = ? and Password = ? and Role = ?",new String[]{Email,Password,Role});
        if(c.getCount()>0) return true;
        return false;
    }
    public long registerCourse(String Coursecode, String Coursetitle,String SessionId, int TheoryCr,int LabCr,String knowledgeArea)
    {
        String [] yr = SessionId.split("-");
        String year = yr[0];
        ContentValues contentValues = new ContentValues();
        contentValues.put("CourseCode",Coursecode);
        contentValues.put("CourseTitle",Coursetitle);
        contentValues.put("TheoryCredit",TheoryCr);
        contentValues.put("LabCredit",LabCr);
        contentValues.put("KnowledgeArea",knowledgeArea);
        long result = database.insert("CourseTable",null ,contentValues);
        ContentValues con = new ContentValues();
        Cursor c = database.query("CourseTable",new String[]{"CourseCode","CourseTitle","TheoryCredit","LabCredit","KnowledgeArea"},"CourseCode = ?", new String[]{Coursecode},null,null,null);
        if(c.moveToFirst()){
            con.put("CourseCode", c.getString(0));
        }
        con.put("Year",year);
        con.put("SessionID",SessionId);
        long query2 = database.insert("SessionTable",null,con);
        return query2;
    }
    public long addCourseSection( String SectionID,String SessionID, String CourseCode, String SectionName)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put("SectionID",SectionID);
        contentValues.put("SessionID",SessionID);
        contentValues.put("CourseCode",CourseCode);
        contentValues.put("SectionName",SectionName);
        long result = database.insert("SectionTable",null ,contentValues);
        return result;
    }
    public long addCourseReq( String CourseCode, String Req)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CourseCode",CourseCode);
        contentValues.put("PreReq",Req);
        long result = database.insert("CourseReqTable",null ,contentValues);
        return result;
    }
    public ContentValues getCourseData(String CourseCode)
    {
        Cursor c=database.query("CourseTable",new String[]{"CourseCode","CourseTitle","TheoryCredit","LabCredit","KnowledgeArea"},"CourseCode = ?", new String[]{CourseCode},null,null,null);
        if(c!=null && c.getCount()!=0)
        {c.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("CourseCode", c.getString(0));
            contentValues.put("CourseTitle", c.getString(1));
            contentValues.put("TheoryCredit", c.getString(2));
            contentValues.put("LabCredit", c.getString(3));
            contentValues.put("KnowledgeArea", c.getString(4));

            return contentValues;}
        else
            return null;

    }

    public ContentValues getCourseSessionID(String CourseCode)
    {
        Cursor c=database.query("SessionTable",new String[]{"SessionID","CourseCode","Year"},"CourseCode = ?", new String[]{CourseCode},null,null,null);
        if(c!=null && c.getCount()!=0)
        {c.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("SessionID", c.getString(0));
            contentValues.put("CourseCode", c.getString(1));
            contentValues.put("Year", c.getString(2));
            return contentValues;}
        else
            return null;

    }
    public long updateCourse(String Coursecode, String Coursetitle,String SessionId, int TheoryCr,int LabCr,String knowledgeArea)
    {
        String [] yr = SessionId.split("-");
        String year = yr[0];
        ContentValues contentValues = new ContentValues();
        contentValues.put("CourseCode",Coursecode);
        contentValues.put("CourseTitle",Coursetitle);
        contentValues.put("TheoryCredit",TheoryCr);
        contentValues.put("LabCredit",LabCr);
        contentValues.put("KnowledgeArea",knowledgeArea);
        long result = database.update("CourseTable", contentValues, "CourseCode = ?",new String[] { Coursecode });

        return result;

    }
    public ContentValues chksection(String Coursecode, String Section)
    {
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select * from SectionTable where CourseCode= ? and SectionName = ? ",new String[]{Coursecode,Section});
        if(c!=null && c.getCount()!=0)
        {c.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("SectionID", c.getString(0));
            contentValues.put("SessionID", c.getString(1));
            contentValues.put("CourseCode", c.getString(2));
            contentValues.put("SectionName", c.getString(3));
            return contentValues;}
        else
            return null;

    }
    public long updateCourseSection( String SectionID,String SessionID, String CourseCode, String SectionName)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SectionID",SectionID);
        contentValues.put("SessionID",SessionID);
        contentValues.put("CourseCode",CourseCode);
        contentValues.put("SectionName",SectionName);
        long result = database.insert("SectionTable",null ,contentValues);
        return result;
    }
    public ContentValues chkreq(String Coursecode,String req)
    {
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select * from CourseReqTable where CourseCode= ? and PreReq = ?",new String[]{Coursecode,req});
        if(c!=null && c.getCount()!=0)
        {c.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("CourseCode", c.getString(0));
            contentValues.put("PreReq", c.getString(1));
            return contentValues;}
        else
            return null;
    }
    public long updateCourseReq( String CourseCode, String PreReq)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CourseCode",CourseCode);
        contentValues.put("PreReq",PreReq);
        long result = database.insert("CourseReqTable",null ,contentValues);
        return result;
    }
    public ArrayList<String> getAllSections(String CourseCode)
    {
        ArrayList<String> arr = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select * from SectionTable where CourseCode= ?",new String[]{CourseCode});
        if(c.getCount()>0)
        {
            while(c.moveToNext()){
                String SectionName = c.getString(c.getColumnIndex("SectionName"));
                arr.add(SectionName);
            }
            return arr;
        }
        else
            return null;
    }
    public long delSection(String Coursecode,String SectionName) {
        long result = database.delete("SectionTable", "CourseCode = ? and SectionName = ?", new String[]{Coursecode,SectionName});
        return result;
    }
    public ArrayList<String> getAllCoursesReq(String CourseCode)
    {
        ArrayList<String> arr = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select * from CourseReqTable where CourseCode= ?",new String[]{CourseCode});
        if(c.getCount()>0)
        {
            while(c.moveToNext()){
                String PreReq = c.getString(c.getColumnIndex("PreReq"));
                arr.add(PreReq);
            }
            return arr;
        }
        else
            return null;
    }
    public long delReq(String Coursecode,String Req) {
        long result = database.delete("CourseReqTable", "CourseCode = ? and PreReq = ?", new String[]{Coursecode,Req});
        return result;
    }
    public long addClo(String CourseCode,int Clo,String Priority,String Desc )
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("CourseCode",CourseCode);
        contentValues.put("CloNo",Clo);
        contentValues.put("CloPriority",Priority);
        contentValues.put("Description",Desc);
        long result = database.insert("CourseCloTable",null ,contentValues);
        return result;
    }
    public ArrayList<String> getDept()
    {
        ArrayList<String> arr = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select DISTINCT Department from TeacherTable ",new String[]{});
        if(c.getCount()>0)
        {
            while(c.moveToNext()){
                String Department = c.getString(c.getColumnIndex("Department"));
                arr.add(Department);
            }
            return arr;
        }
        else
            return null;
    }
    public ArrayList<String> getTeachers( String Department)
    {
        String Role = "Teacher";
        ArrayList<String> arr = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select Name from PersonTable where Role = ? and PersonID IN " +
                "(Select PersonID from TeacherTable where Department LIKE ?)",new String[]{Role,Department});
        if(c.getCount()>0)
        {
            while(c.moveToNext()){
                String Name = c.getString(c.getColumnIndex("Name"));
                arr.add(Name);
            }
            return arr;
        }
        else
            return null;
    }
    public ArrayList<String> getCourses()
    {
        ArrayList<String> arr = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select DISTINCT CourseCode from CourseTable",new String[]{});
        if(c.getCount()>0)
        {
            while(c.moveToNext()){
                String CourseCode = c.getString(c.getColumnIndex("CourseCode"));
                arr.add(CourseCode);
            }
            return arr;
        }
        else
            return null;
    }

    public ArrayList<String> getSection(String CourseCode)
    {
        ArrayList<String> arr = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select SectionName from SectionTable where CourseCode = ?",new String[]{CourseCode});
        if(c.getCount()>0)
        {
            while(c.moveToNext()){
                String Section = c.getString(c.getColumnIndex("SectionName"));
                arr.add(Section);
            }
            return arr;
        }
        else
            return null;
    }
    public long AddTeacherCourse(String Department, String TeacherName,String CourseCode,String SectionName )
    {
        String Role = "Teacher";
        database = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor c = database.rawQuery("Select PersonID from PersonTable where Name= ? and Role = ? and PersonID\n" +
                "IN (Select PersonID from TeacherTable where Department LIKE ?)",new String[]{TeacherName,Role,Department});
        if(c.moveToFirst()){
            contentValues.put("PersonID", c.getString(0));
        }
        Cursor con = database.rawQuery("Select CourseCode from CourseTable where CourseCode = ?",new String[]{CourseCode});
        if(con.moveToFirst()){
            contentValues.put("CourseCode", con.getString(0));
        }
        Cursor conSection = database.rawQuery("Select SectionID from SectionTable where CourseCode = ? and SectionName = ?",new String[]{CourseCode,SectionName});
        if(conSection.moveToFirst()){
            contentValues.put("SectionID", conSection.getString(0));
        }
        database = dbHelper.getWritableDatabase();
        long result = database.insert("TeacherCourseTable",null ,contentValues);
        return result;
    }

    public long AddStudentCourse(String RegNo, String CourseCode,String SectionName )
    {
        database = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor c = database.rawQuery("Select PersonID from StudentTable where RegNo = ?",new String[]{RegNo});
        if(c.moveToFirst()){
            contentValues.put("PersonID", c.getString(0));
        }
        Cursor con = database.rawQuery("Select CourseCode from CourseTable where CourseCode = ?",new String[]{CourseCode});
        if(con.moveToFirst()){
            contentValues.put("CourseCode", con.getString(0));
        }
        Cursor conSection = database.rawQuery("Select SectionID from SectionTable where CourseCode = ? and SectionName = ?",new String[]{CourseCode,SectionName});
        if(conSection.moveToFirst()){
            contentValues.put("SectionID", conSection.getString(0));
        }
        database = dbHelper.getWritableDatabase();
        long result = database.insert("StudentCourseTable",null ,contentValues);
        return result;
    }
    public Cursor getStudentCoursesList(String RegNo)
    {
        database = dbHelper.getReadableDatabase();
        /*Cursor c=database.rawQuery("Select CourseCode,CourseTitle from CourseTable where CourseCode = (Select CourseCode from StudentCourseTable where PersonID = " +
                "(Select PersonID from StudentTable where RegNo = ?)) ", new String[]{RegNo});*/
        Cursor c=database.rawQuery("Select DISTINCT n.CourseCode,t.CourseTitle,S.SectionName from StudentCourseTable n join CourseTable t on n.CourseCode = t.CourseCode join SectionTable s on S.SectionID = n.SectionID where PersonID = " +
                "(SELECT PersonID from StudentTable where RegNo = ?) ", new String[]{RegNo});
        if (c.getCount() == 0) {
            return null;
        }
        if (c != null) {
            //   c.moveToFirst();
        }
        return c;

    }
    public Cursor getTeacherCoursesList(String Email)
    {
        database = dbHelper.getReadableDatabase();
        /*Cursor c=database.rawQuery("Select n.CourseCode,n.CourseTitle,t.SectionName from CourseTable n join SectionTable t on n.CourseCode = t.CourseCode where CourseCode IN (Select CourseCode from TeacherCourseTable where PersonID = " +
                "(SELECT PersonID from TeacherTable where PersonID = (Select PersonID from PersonTable where Email = ?))) ", new String[]{Email});*/
        Cursor c=database.rawQuery("Select DISTINCT n.CourseCode,t.CourseTitle,S.SectionName from TeacherCourseTable n join CourseTable t on n.CourseCode = t.CourseCode join SectionTable s on S.SectionID = n.SectionID where PersonID = " +
                "(SELECT PersonID from TeacherTable where PersonID = (Select PersonID from PersonTable where Email = ?)) ", new String[]{Email});
        if (c.getCount() == 0) {
            return null;
        }
        if (c != null) {
            //   c.moveToFirst();
        }
        return c;

    }

    public Cursor chkCourses()
    {
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select DISTINCT CourseCode from CourseTable",new String[]{});
        if (c.getCount() == 0) {
            return null;
        }
        if (c != null) {
            //   c.moveToFirst();
        }
        return c;

    }

    public ContentValues getLoginData(String Email,String Pass)
    {
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select Role from PersonTable where Email = ? and Password=?", new String[]{Email,Pass});
        if(c!=null && c.getCount()!=0)
        {c.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Role", c.getString(0));
            return contentValues;}
        else
            return null;

    }
    public ContentValues getRegNo(String Email,String Pass)
    {
        database = dbHelper.getReadableDatabase();
        Cursor c=database.rawQuery("Select RegNo from StudentTable where PersonId =  (Select PersonID from PersonTable where Email = ? and Password=?)", new String[]{Email,Pass});
        if(c!=null && c.getCount()!=0)
        {c.moveToFirst();
            ContentValues contentValues = new ContentValues();
            contentValues.put("RegNo", c.getString(0));
            return contentValues;}
        else
            return null;
    }
    public int getAllTeachers()
    {
        String Role = "Teacher";
        database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select COUNT(PersonID) from PersonTable where Role = ?", new String[]{Role});
        int count = 0;
        if(null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }
    public int getAllStudents()
    {
        String Role = "Student";
        database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select COUNT(PersonID) from PersonTable where Role = ?", new String[]{Role});
        int count = 0;
        if(null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }
    public int getAllCourses()
    {
        database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select COUNT(CourseCode) from CourseTable", new String[]{});
        int count = 0;
        if(null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }
    public Cursor getTeacherList()
    {
        database = dbHelper.getReadableDatabase();
        String Role = "Teacher";
        /*Cursor c=database.rawQuery("Select n.CourseCode,n.CourseTitle,t.SectionName from CourseTable n join SectionTable t on n.CourseCode = t.CourseCode where CourseCode IN (Select CourseCode from TeacherCourseTable where PersonID = " +
                "(SELECT PersonID from TeacherTable where PersonID = (Select PersonID from PersonTable where Email = ?))) ", new String[]{Email});*/
        Cursor c=database.rawQuery("Select DISTINCT n.Name,n.Email,s.Department from TeacherTable s join PersonTable n on n.PersonID = s.PersonID where n.Role = ?", new String[]{Role});
        if (c.getCount() == 0) {
            return null;
        }
        if (c != null) {
            //   c.moveToFirst();
        }
        return c;

    }
    public Cursor getStudentList()
    {
        database = dbHelper.getReadableDatabase();
        String Role = "Student";
        /*Cursor c=database.rawQuery("Select n.CourseCode,n.CourseTitle,t.SectionName from CourseTable n join SectionTable t on n.CourseCode = t.CourseCode where CourseCode IN (Select CourseCode from TeacherCourseTable where PersonID = " +
                "(SELECT PersonID from TeacherTable where PersonID = (Select PersonID from PersonTable where Email = ?))) ", new String[]{Email});*/
        Cursor c=database.rawQuery("Select DISTINCT n.Name,n.Email,s.Department from StudentTable s join PersonTable n on n.PersonID = s.PersonID where n.Role = ?", new String[]{Role});
        if (c.getCount() == 0) {
            return null;
        }
        if (c != null) {
            //   c.moveToFirst();
        }
        return c;

    }
    public String getLoginName(String email)
    {
        database = dbHelper.getReadableDatabase();
        /*Cursor c=database.rawQuery("Select n.CourseCode,n.CourseTitle,t.SectionName from CourseTable n join SectionTable t on n.CourseCode = t.CourseCode where CourseCode IN (Select CourseCode from TeacherCourseTable where PersonID = " +
                "(SELECT PersonID from TeacherTable where PersonID = (Select PersonID from PersonTable where Email = ?))) ", new String[]{Email});*/
        Cursor c = database.rawQuery("Select Name from PersonTable where Email = ?", new String[]{email});
        if (c.getCount() == 0) {
            return null;
        }
        String str = "0";
        if (c.moveToFirst()) {
            str = c.getString(c.getColumnIndex("Name"));
        }
        return str;
    }
}
