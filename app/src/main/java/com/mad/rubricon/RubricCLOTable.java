package com.mad.rubricon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RubricCLOTable {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_RUBRIC_ID = "rubric_id";
    public static final String KEY_CLO_ID = "clo_id";

    private final String DATABASE_NAME = "LabGraderDB";
    private final String DATABASE_TABLE = "RubricCLOTable";

    private final int DATABASE_VERSION = 1;
    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public RubricCLOTable(Context context){
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
                    KEY_ROWID + " TEXT PRIMARY KEY, " +
                    KEY_RUBRIC_ID + " INTEGER, " +
                    KEY_CLO_ID + " INTEGER);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public RubricCLOTable open() throws SQLException {
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

    public long createEntry(int id, int cloID,int rubricID){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,id);
        cv.put(KEY_CLO_ID,cloID);
        cv.put(KEY_RUBRIC_ID,rubricID);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData(){
        String [] colomns = new String []{KEY_ROWID,KEY_RUBRIC_ID,KEY_CLO_ID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iCloID = cursor.getColumnIndex(KEY_CLO_ID);
        int iRubricID = cursor.getColumnIndex(KEY_RUBRIC_ID);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            result += cursor.getString(iRowID) + "," + cursor.getString(iCloID)+',' + cursor.getString(iRubricID) + ":";
        }
        cursor.close();

        return result;
    }
    public String getWithTestId(String id){
        String [] colomns = new String []{KEY_ROWID,KEY_CLO_ID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iTestId = cursor.getColumnIndex(KEY_CLO_ID);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if (id.equals(cursor.getString(iTestId))){
                result += cursor.getString(iRowID) + "-";
            }
        }
        cursor.close();

        return result;
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
