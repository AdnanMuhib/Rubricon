package com.mad.rubricon;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Student_List extends AppCompatActivity {
    ListView Student;
    DBManager db;
    ArrayList<CourseData> userList;
    CourseData courseData;
    ArrayList<String> ListArr;
    ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__list);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Toolbar toolbar = findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Teachers List");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        Student = (ListView) findViewById(R.id.listView);
        ListArr = new ArrayList<String>();
        userList = new ArrayList<>();
        Cursor c = db.getStudentList();
        // Checking if no records found
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
            return;
        }
        // Appending records to a string buffer

        while (c.moveToNext()) {
            courseData = new CourseData(c.getString(1),c.getString(0),c.getString(2));
            userList.add(courseData);
        }
        ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_adapter_view,userList);
        Student.setAdapter(adapter);
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}



