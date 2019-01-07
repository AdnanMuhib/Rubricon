package com.mad.rubricon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class StudentMarksTable {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_STUDENT_ID = "student_id";
    public static final String KEY_TEACHER_ID = "teacher_id";
    public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_WEEK_ID = "week_id";
    public static final String KEY_QUESTION_ID = "question_id";
    public static final String KEY_OBTAINED_MARKS = "obtained_marks";

    private final String DATABASE_NAME = "LabGraderDB";
    private final String DATABASE_TABLE = "StudentObtainedMarks";

    private final int DATABASE_VERSION = 2;
    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public StudentMarksTable(Context context){
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
                    KEY_STUDENT_ID + " TEXT, " +
                    KEY_WEEK_ID + " INTEGER, "+
                    KEY_QUESTION_ID + " INTEGER, "+
                    KEY_COURSE_ID + " INTEGER, "+
                    KEY_OBTAINED_MARKS + " DOUBLE, "+
                    KEY_TEACHER_ID + " TEXT);";
            db.execSQL(sqlCode);
        }

        public void create(SQLiteDatabase db){
            String sqlCode = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY, " +
                    KEY_STUDENT_ID + " TEXT, " +
                    KEY_WEEK_ID + " INTEGER, "+
                    KEY_QUESTION_ID + " INTEGER, "+
                    KEY_COURSE_ID + " INTEGER, "+
                    KEY_OBTAINED_MARKS + " DOUBLE, "+
                    KEY_TEACHER_ID + " TEXT);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public StudentMarksTable open() throws SQLException {
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

    public int checkStudentEntry(int studentId, String teacherId, String courseId, int weekId, int questionId){
        String [] colomns = new String []{KEY_ROWID,
                KEY_STUDENT_ID,
                KEY_WEEK_ID,
                KEY_QUESTION_ID,
                KEY_COURSE_ID,
                KEY_TEACHER_ID};
        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE,
                colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iStdId = cursor.getColumnIndex(KEY_STUDENT_ID);
        int iWeekId = cursor.getColumnIndex(KEY_WEEK_ID);
        int iQstnId = cursor.getColumnIndex(KEY_QUESTION_ID);
        int iCourseId = cursor.getColumnIndex(KEY_COURSE_ID);
        int iTeachId = cursor.getColumnIndex(KEY_TEACHER_ID);

        int check = -1;

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if(cursor.getInt(iStdId) == studentId &&
                    cursor.getInt(iWeekId) == weekId &&
                    cursor.getString(iTeachId) == teacherId &&
                    cursor.getString(iCourseId) == courseId &&
                    cursor.getInt(iWeekId) == weekId &&
                    cursor.getInt(iQstnId) == questionId
                    ) {
                check = cursor.getInt(iRowID);
                break;
            }
        }
        cursor.close();


        return check;
    }

    public ArrayList<String> getStudentsMarks(String teacherId, String courseId, int weekId, int questionId){
        ArrayList<String> cols = new  ArrayList<>();
        String [] colomns = new String []{KEY_ROWID,
                KEY_STUDENT_ID,
                KEY_WEEK_ID,
                KEY_QUESTION_ID,
                KEY_COURSE_ID,
                KEY_TEACHER_ID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,
                null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iStdId = cursor.getColumnIndex(KEY_STUDENT_ID);
        int iWeekId = cursor.getColumnIndex(KEY_WEEK_ID);
        int iQstnId = cursor.getColumnIndex(KEY_QUESTION_ID);
        int iCourseId = cursor.getColumnIndex(KEY_COURSE_ID);
        int iTeachId = cursor.getColumnIndex(KEY_TEACHER_ID);
        int iMarks = cursor.getColumnIndex(KEY_OBTAINED_MARKS);


        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if(
                    cursor.getInt(iWeekId) == weekId &&
                    cursor.getString(iTeachId) == teacherId &&
                    cursor.getString(iCourseId) == courseId &&
                    cursor.getInt(iQstnId) == questionId
                    ) {
                cols.add(cursor.getString(iStdId)+","+cursor.getString(iMarks));
            }
        }
        cursor.close();
        return cols;
    }

    public long createEntry(int studentId, String teacherId, String courseId, int weekId, int questionId, double obtainedMarks){
        ContentValues cv = new ContentValues();
        cv.put(KEY_STUDENT_ID,studentId);
        cv.put(KEY_TEACHER_ID,teacherId);
        cv.put(KEY_COURSE_ID,courseId);
        cv.put(KEY_WEEK_ID,weekId);
        cv.put(KEY_QUESTION_ID,questionId);
        cv.put(KEY_OBTAINED_MARKS,obtainedMarks);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }


    public void updateEntry(int rowId, double obtainedMarks){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OBTAINED_MARKS,obtainedMarks);
        String[] args = new String[]{String.valueOf(rowId)};
        ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=?",args);
    }

    public void updateDatabase(ArrayList<Integer> studentId, String teacherId, String courseId,
                               int weekId, int questionId, ArrayList<Double> obtainedMarks){
        for (int i = 0; i < studentId.size(); i++){
            int rowId = checkStudentEntry(Integer.parseInt(studentId.get(i).toString()),
                    teacherId, courseId, weekId, questionId);
            if(rowId > -1){
                updateEntry(rowId, Double.parseDouble(obtainedMarks.get(i).toString()));
            }
            else {
                createEntry(Integer.parseInt(studentId.get(i).toString()), teacherId, courseId,
                        weekId, questionId, Double.parseDouble(obtainedMarks.get(i).toString()));
            }
        }
    }

//
//    public String getData(){
//        String [] colomns = new String []{KEY_ROWID,KEY_RUBRIC_CLO_ID,KEY_MARKS,KEY_LAB_ID};
//
//        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);
//
//        String result = "";
//
//        int iRowID = cursor.getColumnIndex(KEY_ROWID);
//        int iRubricCloID = cursor.getColumnIndex(KEY_RUBRIC_CLO_ID);
//        int iMarks = cursor.getColumnIndex(KEY_MARKS);
//        int iLab = cursor.getColumnIndex(KEY_LAB_ID);
//
//        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
//            result += cursor.getString(iRowID) + "," + cursor.getString(iRubricCloID)+','+cursor.getString(iLab) +
//                    ',' + cursor.getString(iMarks) + ":";
//        }
//        cursor.close();
//
//        return result;
//    }

//    public ArrayList<String> getQuestions(int labId){
//        String [] colomns = new String []{KEY_ROWID,KEY_LAB_ID};
//
//        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);
//        int count = 1;
//
//        int iRowID = cursor.getColumnIndex(KEY_ROWID);
//        int iLab = cursor.getColumnIndex(KEY_LAB_ID);
//
//        ArrayList<String> values = new ArrayList<>();
//
//        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
//            if (cursor.getInt(iLab) == labId) {
//                String value = cursor.getInt(iRowID) + ",Question " + count;
//                values.add(value);
//                count++;
//            }
//        }
//
//        return values;
//    }

    public int getCount(){
        String [] colomns = new String []{KEY_ROWID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);
        int count = 0;
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            count++;
        }
        return count;
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
