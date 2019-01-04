package com.mad.Rubricon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StudentCourseList extends AppCompatActivity {
    ListView Student;
    DBManager db;
    ArrayList<CourseData> userList;
    CourseData courseData;
    ArrayList<String> ListArr;
    ArrayAdapter<String> itemsAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signoutmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item:
                Intent j = new Intent(this,Stats.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Student = (ListView) findViewById(R.id.listView);
        Intent StudentReg = getIntent();
        String RegNo = StudentReg.getStringExtra("RegNo");
        ListArr = new ArrayList<String>();
        userList = new ArrayList<>();
        Cursor c = db.getStudentCoursesList(RegNo);
        int rows = c.getCount();

        // Checking if no records found
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
            return;
        }
        // Appending records to a string buffer

        while (c.moveToNext()) {
            courseData = new CourseData(c.getString(0),c.getString(1),c.getString(2));
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
