package com.mad.rubricon;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LabEvalEnterMarksActivity extends AppCompatActivity {
    private  int courseId;
    private String weekId;
    private int teacherId;
    private String questionId;

    TextView courseTitleView;
    ListView coursesListView;
    TextView weekQuestionView;
    ArrayList<CourseMarks> courseMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_enter_marks);

        courseId = getIntent().getIntExtra("CourseId", 0);
        weekId = getIntent().getStringExtra("CourseWeek");
        teacherId = getIntent().getIntExtra("TeacherId",0);
        questionId = getIntent().getStringExtra("QuestionId");

        courseTitleView = (TextView) findViewById(R.id.textViewCourseTitle);
        weekQuestionView = (TextView) findViewById(R.id.textViewWeekQuestion);

        courseTitleView.setText("Course Id: " + courseId);
        weekQuestionView.setText("Week: " + weekId + ", Question: " + questionId);

        courseMarks = getMarksList();
        EvaluationCustomAdapter adapter = new EvaluationCustomAdapter(courseMarks, this);
        coursesListView = (ListView) findViewById(R.id.listViewStudents);
        coursesListView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar_lab_marks_enter_activity);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Lab Marking");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        Button save = findViewById(R.id.btnUpdateMarks);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClick();
            }
        });
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

    public void onSaveClick(){
        ArrayList<Integer> stdId = new ArrayList<>();
        ArrayList<Double> stdMarks = new ArrayList<>();
        int count = coursesListView.getAdapter().getCount();
        for(int i = 0; i < count; i++){
            View item = coursesListView.getChildAt(i);
            EditText marks = (EditText) item.findViewById(R.id.editTextMarks);
            stdId.add(courseMarks.get(i).getId());
            stdMarks.add(Double.parseDouble(marks.getText().toString()));
        }

        StudentMarksTable table = new StudentMarksTable(this);
        table.open();
        table.updateDatabase(stdId, teacherId, courseId, Integer.parseInt(weekId),
                Integer.parseInt(questionId), stdMarks);
    }



    public ArrayList<CourseMarks> getMarksList(){
        ArrayList<CourseMarks> marksLst = new ArrayList<CourseMarks>();

        // Code to Get the Marks of Every Student Goes Here

        // Dummy Data for Student Marks
        marksLst.add(new CourseMarks(1,"2015-CS-51",5));
        marksLst.add(new CourseMarks(2,"2015-CS-52",6));
        marksLst.add(new CourseMarks(3,"2015-CS-53",7));
        marksLst.add(new CourseMarks(4,"2015-CS-54",8));
        marksLst.add(new CourseMarks(5,"2015-CS-55",9));
        marksLst.add(new CourseMarks(6,"2015-CS-56",10));
        marksLst.add(new CourseMarks(7,"2015-CS-57",4));
        marksLst.add(new CourseMarks(8,"2015-CS-58",5));
        return  marksLst;
    }
}
