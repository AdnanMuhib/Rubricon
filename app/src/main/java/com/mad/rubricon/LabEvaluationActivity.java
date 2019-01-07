package com.mad.rubricon;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LabEvaluationActivity extends AppCompatActivity {
    String requiredOperation;
    String teacherId = "";
    DBManager db;
    Course course;
    ArrayList<Course> courseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_evaluation);
        try {
            db = new DBManager(this);
            db.open();
        }catch(Exception e)
        {
        }
        Intent i = getIntent();
        teacherId = i.getStringExtra("TeacherId");
        ArrayList<Course> courses = getCoursesList();
        CourseListCustomAdapter adapter = new CourseListCustomAdapter(courses, this);

        TextView teachView = findViewById(R.id.textViewTeacherName);
        teachView.setText(teacherId);
        requiredOperation = getIntent
                ().getStringExtra("ActivityName");

        ListView coursesListView = (ListView) findViewById(R.id.mCourseList);
        coursesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (requiredOperation.equals("LabEvaluation"))
                {
                    TextView crseId = parent.getChildAt(position).findViewById(R.id.textViewCourseId);
                    Intent intent = new Intent(LabEvaluationActivity.this, LabEvalSelectWeekActivity.class);
                    intent.putExtra("requiredOperation", "LabEvaluation");
                    intent.putExtra("crsId",crseId.getText().toString());
                    intent.putExtra("teacherId",teacherId);
                    startActivity(intent);
                } else if(requiredOperation.equals("LabCreation")){
                    Intent intent = new Intent(LabEvaluationActivity.this, LabEvalSelectWeekActivity.class);
                    TextView crseId = parent.getChildAt(position).findViewById(R.id.textViewCourseId);
                    intent.putExtra("requiredOperation", "LabCreation");
                    intent.putExtra("crsId",crseId.getText().toString());
                    intent.putExtra("teacherId",teacherId);
                    startActivity(intent);

                } else{
                    Intent intent = new Intent(LabEvaluationActivity.this, LabReportActivity.class);
                    intent.putExtra("crsId","1");
                    startActivity(intent);
                }

            }
        });
        coursesListView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(requiredOperation.equals("LabEvaluation"))
            actionbar.setTitle("Lab Evaluation");
        else
            actionbar.setTitle("Lab Reporting");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public ArrayList<Course> getCoursesList(){
        // get list of Courses  from Database

        courseList = new ArrayList<>();


        Cursor c = db.getTeacherCoursesList(teacherId);
        // Checking if no records found
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
            return new ArrayList<>();
        }
        // Appending records to a string buffer

        int count = 0;

        while (c.moveToNext()) {
            course = new Course(++count,c.getString(0),c.getString(1));
            courseList.add(course);
        }

        // Dummy Data for Testing
        ArrayList<Course> crses = new ArrayList<Course>();
        crses.add(new Course(1,"CS-125L", "Introduction to Computing"));
        crses.add(new Course(2,"CS-112L", "Programming Fundamentals"));
        crses.add(new Course(3,"CS-121L", "Assembly Programming"));
        crses.add(new Course(4,"CS-123L", "Object Oriented Programming"));
        crses.add(new Course(5,"CS-145L", "Data Structures"));

        return courseList;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
     public void onCourseClick(Course crsItem){

     }
}
