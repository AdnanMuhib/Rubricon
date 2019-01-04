package com.mad.rubricon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LabEvalEnterMarksActivity extends AppCompatActivity {
    private  int courseId;
    private String weekId;
    private String questionId;

    TextView courseTitleView;
    TextView weekQuestionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_eval_enter_marks);

        courseId = getIntent().getIntExtra("CourseId", 0);
        weekId = getIntent().getStringExtra("CourseWeek");
        questionId = getIntent().getStringExtra("QuestionId");

        courseTitleView = (TextView) findViewById(R.id.textViewCourseTitle);
        weekQuestionView = (TextView) findViewById(R.id.textViewWeekQuestion);

        courseTitleView.setText("Course Id: " + courseId);
        weekQuestionView.setText("Week: " + weekId + ", Question: " + questionId);

        ArrayList<CourseMarks> courseMarks = getMarksList();
        EvaluationCustomAdapter adapter = new EvaluationCustomAdapter(courseMarks, this);
        ListView coursesListView = (ListView) findViewById(R.id.listViewStudents);
        coursesListView.setAdapter(adapter);
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
        marksLst.add(new CourseMarks(1,"2015-CS-51",5));
        marksLst.add(new CourseMarks(2,"2015-CS-52",6));
        marksLst.add(new CourseMarks(3,"2015-CS-53",7));
        marksLst.add(new CourseMarks(4,"2015-CS-54",8));
        marksLst.add(new CourseMarks(5,"2015-CS-55",9));
        marksLst.add(new CourseMarks(6,"2015-CS-56",10));
        marksLst.add(new CourseMarks(7,"2015-CS-57",4));
        marksLst.add(new CourseMarks(8,"2015-CS-58",5));
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
