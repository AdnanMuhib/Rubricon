package com.mad.rubricon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CriteriaTable {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE_ID = "_title";
    public static final String KEY_DESCRIPTION_ID = "_description";
    public static final String KEY_RUBRIC_CLO_ID = "rubric_clo_id";

    private final String DATABASE_NAME = "LabGraderDB";
    private final String DATABASE_TABLE = "CriteriaTable";

    private final int DATABASE_VERSION = 1;
    private CriteriaTable.DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public CriteriaTable(Context context){
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
                    KEY_TITLE_ID + " TEXT, " +
                    KEY_DESCRIPTION_ID+" TEXT, "+
                    KEY_RUBRIC_CLO_ID + " INTEGER);";
            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public CriteriaTable open() throws SQLException {
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

    public long createEntry(int id, String title,int rubricCloID, String des){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,id);
        cv.put(KEY_TITLE_ID,title);
        cv.put(KEY_DESCRIPTION_ID,des);
        cv.put(KEY_RUBRIC_CLO_ID,rubricCloID);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData(){
        String [] colomns = new String []{KEY_ROWID,KEY_TITLE_ID,KEY_DESCRIPTION_ID,KEY_RUBRIC_CLO_ID};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iRubricID = cursor.getColumnIndex(KEY_RUBRIC_CLO_ID);
        int iTitle = cursor.getColumnIndex(KEY_TITLE_ID);
        int iDescription = cursor.getColumnIndex(KEY_DESCRIPTION_ID);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            result += cursor.getString(iRowID) + "," + cursor.getString(iRubricID)+ ','+ cursor.getString(iTitle) +','
                    + cursor.getString(iDescription) + ":";
        }
        cursor.close();

        return result;
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
