package com.example.tanzeel.Rubricon;

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
                Intent j = new Intent(this,MainActivity.class);
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
        Cursor c = db.getStudentCoursesList(RegNo);
        // Checking if no records found
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
            return;
        }
        // Appending records to a string buffer

        while (c.moveToNext()) {
            ListArr.add(c.getString(0) + " | " + c.getString(1) + " | " + c.getString(2));
        }
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ListArr);
        Student.setAdapter(itemsAdapter);
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
