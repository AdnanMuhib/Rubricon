package com.mad.Rubricon;

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

public class AllocateCourses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner Dept,Teacher,Coursename,Section;
    ListView Listcourses;
    DBManager db;
    ArrayAdapter<String> adapter;
    ArrayList<String> ListArr;
    ArrayAdapter<String> adapt;
    ArrayAdapter<String> CourseAdapter;
    ArrayAdapter<String> sectionAdapter;
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
        setContentView(R.layout.activity_allocate_courses);
        Dept = (Spinner) findViewById(R.id.dept_spinner);
        Teacher = (Spinner) findViewById(R.id.teacher_spinner);
        Coursename = (Spinner) findViewById(R.id.course_spinner);
        Section = (Spinner) findViewById(R.id.section_spinner);
        Listcourses = (ListView) findViewById(R.id.listView);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }

        ArrayList<String> arr = new ArrayList<String>();
        arr = db.getDept();
        if((arr.size()>0)) {
            adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
            Dept.setAdapter(adapter);
        }
        else {
            showMessage("Error", "No Department Found");
        }
        Dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Department = Dept.getSelectedItem().toString();
                loadTeachers(Department);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> coursearr = new ArrayList<String>();
        coursearr = db.getCourses();
        if((coursearr.size()>0)) {
            CourseAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,coursearr);
            Coursename.setAdapter(CourseAdapter);
        }
        else {
            showMessage("Error", "No Course Found");
        }
        Coursename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String CourseCode = Coursename.getSelectedItem().toString();
                loadSections(CourseCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ListArr = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ListArr);
        Listcourses.setAdapter(itemsAdapter);


    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void loadTeachers(String Department)
    {
        ArrayList<String> arr = new ArrayList<String>();
        arr = db.getTeachers(Department);
        adapt = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arr);
        if((arr.size()>0)) {
            adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Teacher.setAdapter(adapt);
        }
        else {
            showMessage("Error", "No Teacher Found");
        }
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
    public void assignCourses(View v)
    {
        long result = db.AddTeacherCourse(Dept.getSelectedItem().toString(),Teacher.getSelectedItem().toString(),Coursename.getSelectedItem().toString(),Section.getSelectedItem().toString());
        if (result == -1) {
            showMessage("Error", "Record not Added");
        } else {
            showMessage("Success", "Record Added Successfully");
            ListArr.add(Dept.getSelectedItem().toString() + " | " + Teacher.getSelectedItem().toString() + " | " + Coursename.getSelectedItem().toString() + " | " + Section.getSelectedItem().toString());
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void submitCourses(View v)
    {
        Intent i = new Intent(this,Courses.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
