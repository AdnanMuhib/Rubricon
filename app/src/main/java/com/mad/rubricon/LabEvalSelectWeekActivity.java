package com.mad.rubricon;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LabEvalSelectWeekActivity extends AppCompatActivity {
    private  String courseId = "2";
    private  String teacherId;
    String requiredOperation;
    ArrayList<String> courseWeeksList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_select_week);
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        courseId = getIntent().getStringExtra("crsId");
        teacherId = getIntent().getStringExtra("teacherId");
        fab = findViewById(R.id.newLab);
        requiredOperation = getIntent().getStringExtra("requiredOperation");

        // find the Weekly Labs using Lab Course Id from Data Base
        courseWeeksList = GetCourseWeeks();
//        courseWeeksList.add("Course Id" + courseId);


        if(courseWeeksList.size()>0){
            ArrayList<String> labTitles = new ArrayList<>();
            for(int i=0;i<courseWeeksList.size();i++){
                String[] split = courseWeeksList.get(i).split(",");
                labTitles.add(split[1]);
            }
            ListView mCourseListView  = (ListView) findViewById(R.id.listViewCourseWeeks);

            // when a week is clicked from the list
            mCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String value = (String)parent.getItemAtPosition(position);
                    Log.i("DebugLog", value);

                    // code to go to next activity where Questions will be Displayed for Evaluation
                    GoToQuestionsActivity(courseWeeksList.get(position));
                }
            });
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.single_item_list, labTitles);

            mCourseListView.setAdapter(adapter);

        }

        Toolbar toolbar = findViewById(R.id.toolbar_lab_week_list);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Lab Weeks");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }


    @Override
    protected void onResume() {
        super.onResume();
        courseWeeksList = GetCourseWeeks();
        if(courseWeeksList.size()>0){
            ArrayList<String> labTitles = new ArrayList<>();
            for(int i=0;i<courseWeeksList.size();i++){
                String[] split = courseWeeksList.get(i).split(",");
                labTitles.add(split[1]);
            }
            ListView mCourseListView  = (ListView) findViewById(R.id.listViewCourseWeeks);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.single_item_list, labTitles);
            mCourseListView.setAdapter(adapter);

        }
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

    public ArrayList<String> GetCourseWeeks(){

        // Get a List of Lab Weeks using course Id from Database

        LabTable table = new LabTable(this);
        table.open();
        ArrayList<String> labs = table.getLabId(teacherId,courseId);
        table.close();

        // Dummy Data for Testing
        ArrayList<String> lst = new ArrayList<String>();

        lst.add("Lab Week 1");
        lst.add("Lab Week 2");
        lst.add("Lab Week 3");
        lst.add("Lab Week 4");
        lst.add("Lab Week 5");

        return  labs;
    }

    public void createLab(View view){
        Intent intent = new Intent(LabEvalSelectWeekActivity.this, LabActivity.class);
        intent.putExtra("teacherId", teacherId);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }
    public void GoToQuestionsActivity(String val){
        if(!requiredOperation.equals("LabCreation")){
            Intent intent = new Intent(this, LabEvalSelectQuestionActivity.class);
            String[] split = val.split(",");
            intent.putExtra("CourseWeek", split[1]);
            intent.putExtra("CourseWeekId", split[0]);
            intent.putExtra("CourseId", courseId);
            intent.putExtra("TeacherId", teacherId);

            Log.i("DebugLog", "Selected Course week is:  "+ val);
            startActivity(intent);
        }
    }


}
