package com.mad.rubricon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LabTable {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_LAB_TITLE = "lab_title";
    public static final String KEY_MARKS = "_marks";
    public static final String KEY_MARKS_WEIGHT = "marks_weight";
    public static final String KEY_TEACHER_ID = "teacher_id";
    public static final String KEY_SECTION = "_section";
    public static final String KEY_COURSE_ID = "course_id";

    private final String DATABASE_NAME = "LabGraderDB";
    private final String DATABASE_TABLE = "LabTable";

    private final int DATABASE_VERSION = 2
            ;
    private LabTable.DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public LabTable(Context context){
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
                    KEY_LAB_TITLE + " TEXT, "+
                    KEY_COURSE_ID + " TEXT, "+
                    KEY_SECTION + " TEXT, "+
                    KEY_TEACHER_ID + " TEXT, "+
                    KEY_MARKS_WEIGHT + " DOUBLE, " +
                    KEY_MARKS + " DOUBLE);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public LabTable open() throws SQLException {
        this.ourHelper = new DBHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        //ourHelper.onUpgrade(ourDatabase,1,DATABASE_VERSION);
        return this;
    }
    public void create(){
        this.ourHelper = new DBHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        this.delete();
        this.ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        //this.close();
        ourHelper.onCreate(ourDatabase);
    }

    public void close(){
        this.ourHelper.close();
    }

    public long createEntry(int id, double marks,double mWeight, String title, String teacherId, String courseID, String section){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,id);
        cv.put(KEY_LAB_TITLE,title);
        cv.put(KEY_MARKS,marks);
        cv.put(KEY_MARKS_WEIGHT,mWeight);
        cv.put(KEY_TEACHER_ID,teacherId);
        cv.put(KEY_COURSE_ID, courseID);
        cv.put(KEY_SECTION, section);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData(){
        String [] colomns = new String []{KEY_ROWID,KEY_LAB_TITLE,KEY_MARKS_WEIGHT,KEY_MARKS,KEY_COURSE_ID,KEY_TEACHER_ID,KEY_SECTION};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iTitle = cursor.getColumnIndex(KEY_LAB_TITLE);
        int iMarksWeight = cursor.getColumnIndex(KEY_MARKS_WEIGHT);
        int iMarks = cursor.getColumnIndex(KEY_MARKS);
        int iCourseId = cursor.getColumnIndex(KEY_COURSE_ID);
        int iTeacherId = cursor.getColumnIndex(KEY_TEACHER_ID);
        int iSection = cursor.getColumnIndex(KEY_SECTION);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            result += cursor.getInt(iRowID) +',' +cursor.getString(iTitle)+ "," + cursor.getString(iMarksWeight)+',' +
                    cursor.getString(iMarks) + ',' +cursor.getString(iCourseId) + ',' +cursor.getString(iTeacherId) +
                    ',' +cursor.getString(iSection) + ":";
        }
        cursor.close();

        return result;
    }

    public ArrayList<String> getLabId(String teacher, String courseId){
        String [] colomns = new String []{KEY_ROWID, KEY_LAB_TITLE,KEY_TEACHER_ID,KEY_COURSE_ID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,KEY_ROWID);

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iTitle = cursor.getColumnIndex(KEY_LAB_TITLE);
        int iCourseId = cursor.getColumnIndex(KEY_COURSE_ID);
        int iTeacherId = cursor.getColumnIndex(KEY_TEACHER_ID);

        ArrayList<String> values = new ArrayList<>();

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if (cursor.getString(iTeacherId).equals(teacher) && cursor.getString(iCourseId).equals(courseId)) {
                String value = cursor.getInt(iRowID) + "," + cursor.getString(iTitle);
                values.add(value);
            }
        }

        return values;
    }

    public int getCount(){
        String [] colomns = new String []{KEY_ROWID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,KEY_ROWID);

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
