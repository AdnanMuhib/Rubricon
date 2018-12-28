package com.example.tanzeel.Rubricon;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class StudentCourses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner Course,Section;
    ArrayAdapter<String> CourseAdapter;
    ArrayAdapter<String> sectionAdapter;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> ListArr;
    DBManager db;
    ListView Courses;

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
        setContentView(R.layout.activity_student_courses);
        Course = (Spinner) findViewById(R.id.CourseSpinner);
        Section = (Spinner) findViewById(R.id.CourseSection);
        Courses = (ListView) findViewById(R.id.CoursesList);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Intent StudentReg = getIntent();
        String RegNo = StudentReg.getStringExtra("RegNo");

        ArrayList<String> coursearr = new ArrayList<String>();
        coursearr = db.getCourses();
        if((coursearr.size()>0)) {
            CourseAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,coursearr);
            Course.setAdapter(CourseAdapter);
        }
        else {
            showMessage("Error", "No Course Found");
        }
        Course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String CourseCode = Course.getSelectedItem().toString();
                loadSections(CourseCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ListArr = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ListArr);
        Courses.setAdapter(itemsAdapter);
    }
    public void loadSections(String CourseCode)
    {
        ArrayList<String> arr = new ArrayList<String>();
        arr = db.getSection(CourseCode);
        if((arr.size()>0)) {
            sectionAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
            Section.setAdapter(sectionAdapter);
        }
        else {
            showMessage("Error", "No Section Found");
        }
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void AddCourses(View v)
    {
        Intent StudentReg = getIntent();
        String RegNo = StudentReg.getStringExtra("RegNo");
        long result = db.AddStudentCourse(RegNo,Course.getSelectedItem().toString(),Section.getSelectedItem().toString());
        if (result == -1) {
            showMessage("Error", "Record not Added");
        } else {
            showMessage("Success", "Record Added Successfully");
            ListArr.add(RegNo + " | " + Course.getSelectedItem().toString() + " | " + Section.getSelectedItem().toString());
            itemsAdapter.notifyDataSetChanged();
        }
    }
    public void submitCourse(View v)
    {
        Intent StudentReg = getIntent();
        String RegNo = StudentReg.getStringExtra("RegNo");
        Intent i = new Intent(this,StudentPanel.class);
        i.putExtra("RegNo",RegNo);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
