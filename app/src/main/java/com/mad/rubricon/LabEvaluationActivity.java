package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class LabEvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_evaluation);
        ArrayList<Course> courses = getCoursesList();
        CourseListCustomAdapter adapter = new CourseListCustomAdapter(courses, this);
        ListView coursesListView = (ListView) findViewById(R.id.mCourseList);
        coursesListView.setAdapter(adapter);
    }
    public ArrayList<Course> getCoursesList(){
        // get list of Courses  from Database

            // code goes here....

        // Dummy Data for Testing
        ArrayList<Course> crses = new ArrayList<Course>();
        crses.add(new Course(1,"CS-125L", "Introduction to Computing"));
        crses.add(new Course(2,"CS-112L", "Programming Fundamentals"));
        crses.add(new Course(3,"CS-121L", "Assembly Programming"));
        crses.add(new Course(4,"CS-123L", "Object Oriented Programming"));
        crses.add(new Course(5,"CS-145L", "Data Structures"));

        return crses;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }
}
