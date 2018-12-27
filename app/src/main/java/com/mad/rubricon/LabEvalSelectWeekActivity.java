package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LabEvalSelectWeekActivity extends AppCompatActivity {
    private  int courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_select_week);
        courseId = Integer.parseInt(getIntent().getStringExtra("crsId"));

        // find the Weekly Labs using Lab Course Id from Data Base
        ArrayList<String> courseWeeksList = GetCourseWeeks();
        courseWeeksList.add("Course Id" + courseId);
        ListView mCourseListView  = (ListView) findViewById(R.id.listViewCourseWeeks);

        // when a week is clicked from the list
        mCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String)parent.getItemAtPosition(position);
                Log.i("DebugLog", value);

                // code to go to next activity where Questions will be Displayed for Evaluation
                GoToQuestionsActivity(value);
            }
        });
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseWeeksList);

        mCourseListView.setAdapter(adapter);

    }

    public ArrayList<String> GetCourseWeeks(){

        // Get a List of Lab Weeks using course Id from Database
            // code goes here

        // Dummy Data for Testing
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("Lab Week 1");
        lst.add("Lab Week 2");
        lst.add("Lab Week 3");
        lst.add("Lab Week 4");
        lst.add("Lab Week 5");

        return  lst;
    }
    public void GoToQuestionsActivity(String val){
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("CourseWeek", val);
        Log.i("DebugLog", "Selected Course week is:  "+ val);
        //startActivity(intent);
    }


}
