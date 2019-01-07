package com.mad.rubricon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RubricTable {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_RUBRIC_TITLE_ID = "rubric_title";
    public static final String KEY_TEACHER_ID = "teacher_id";
    public static final String KEY_SECTION = "_section";

    private final String DATABASE_NAME = "LabGraderDB";
    private final String DATABASE_TABLE = "RubricTable";

    private final int DATABASE_VERSION = 2;
    private RubricTable.DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public RubricTable(Context context){
        this.ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context){
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*

            CREATE TABLE AccountsTable(_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    person_name TEXT NOT NULL, _cell TEXT NOT NULL)

            */
            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY, " +
                    KEY_COURSE_ID + " TEXT, " +
                    KEY_TEACHER_ID + " TEXT, " +
                    KEY_SECTION + " TEXT, " +
                    KEY_RUBRIC_TITLE_ID + " TEXT);";
            db.execSQL(sqlCode);
        }

        public void create(SQLiteDatabase db) {
            /*

            CREATE TABLE AccountsTable(_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    person_name TEXT NOT NULL, _cell TEXT NOT NULL)

            */
            String sqlCode = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY, " +
                    KEY_COURSE_ID + " TEXT, " +
                    KEY_TEACHER_ID + " TEXT, " +
                    KEY_SECTION + " TEXT, " +
                    KEY_RUBRIC_TITLE_ID + " TEXT);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public RubricTable open() throws SQLException {
        this.ourHelper = new DBHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        //ourHelper.onUpgrade(ourDatabase,1,DATABASE_VERSION);
        return this;
    }
    public void create(){
        this.ourHelper = new DBHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        ourHelper.create(ourDatabase);
    }

    public void close(){
        this.ourHelper.close();
    }

    public long createEntry(int id, String couseID,String rubricTitle, String teacher_id, String section){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,id);
        cv.put(KEY_COURSE_ID,couseID);
        cv.put(KEY_RUBRIC_TITLE_ID,rubricTitle);
        cv.put(KEY_TEACHER_ID, teacher_id);
        cv.put(KEY_SECTION, section);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData(){
        String [] colomns = new String []{KEY_ROWID,KEY_COURSE_ID,KEY_RUBRIC_TITLE_ID,KEY_TEACHER_ID, KEY_SECTION};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iRubricTitleID = cursor.getColumnIndex(KEY_RUBRIC_TITLE_ID);
        int iCourseID = cursor.getColumnIndex(KEY_COURSE_ID);
        int iTeacherID = cursor.getColumnIndex(KEY_TEACHER_ID);
        int iSection = cursor.getColumnIndex(KEY_SECTION);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            result += cursor.getString(iRowID) + "," + cursor.getString(iRubricTitleID)+"," + cursor.getString(iCourseID)
                    +","+ cursor.getString(iTeacherID)+ "," + cursor.getString(iSection) + ":";
        }
        cursor.close();

        return result;
    }

    public String getIds(String courseId, String teacherId){
        String [] colomns = new String []{KEY_ROWID,KEY_COURSE_ID,KEY_TEACHER_ID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iCourse = cursor.getColumnIndex(KEY_COURSE_ID);
        int iTeacher = cursor.getColumnIndex(KEY_TEACHER_ID);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if (cursor.getString(iTeacher).equals(teacherId) && cursor.getString(iCourse).equals(courseId)) {
                result += cursor.getInt(iRowID) + ",";
            }
        }

        return result;
    }

    public int getCount(){
        String [] colomns = new String []{KEY_ROWID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,KEY_ROWID);

        int count = 0;
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            count++;
        }
        return count+1;
    }

    public ArrayList<RubricCLO> sort(ArrayList<RubricCLO> list){
        for (int i=1; i<=list.size(); i++){
            int index = find(list,i);
            RubricCLO q = list.get(index);
            list.set(index,list.get(i-1));
            list.set(i-1,q);
        }
        return list;
    }

    public int find(ArrayList<RubricCLO> list, int number){
        for (RubricCLO rubricCLO : list){

        }
        return 0;
    }

    public long deleteEntry(String rowId){
        return this.ourDatabase.delete(DATABASE_TABLE,KEY_ROWID + "=?",new String[]{rowId});
    }
    public void delete(){
        ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }
}
