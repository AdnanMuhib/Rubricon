package com.mad.rubricon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBManager db;
    public DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout mDrawerLayout;
    BarChart barchart;
    ListView Teacher;
    ArrayList<CourseData> userList;
    CourseData courseData;
    ArrayList<String> ListArr;
    String teacherEmail;
    ArrayAdapter<String> itemsAdapter;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.login,menu);
        inflater.inflate(R.menu.signoutmenu,menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_teacher_layout);
        try {
            db = new DBManager(this);
            db.open();
        } catch (Exception e) {
        }
        Toolbar toolbar = findViewById(R.id.toolbarTeacher);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Teacher Panel");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Teacher = (ListView) findViewById(R.id.listView);
        Intent Email = getIntent();
        teacherEmail = Email.getStringExtra("Email");
        ListArr = new ArrayList<String>();
        userList = new ArrayList<>();
        Cursor c = db.getTeacherCoursesList(teacherEmail);
        // Checking if no records found
        if (c == null) {
            showMessage("Error", "No records found");
        }
        else {
            // Appending records to a string buffer

            while (c.moveToNext()) {
                courseData = new CourseData(c.getString(0), c.getString(1), c.getString(2));
                userList.add(courseData);
            }
        }
        ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_adapter_view,userList);
        Teacher.setAdapter(adapter);
        int NoTeachers = db.getAllTeachers();
        int NoStudents = db.getAllStudents();
        int NoCourses = db.getAllCourses();
        barchart = (BarChart) findViewById(R.id.bargraph);
        barchart.setDrawBarShadow(false);
        barchart.setDrawValueAboveBar(true);
        barchart.setMaxVisibleValueCount(50);
        barchart.setPinchZoom(false);
        barchart.setDrawGridBackground(true);
        barchart.getDescription().setText("");
        ArrayList<BarEntry> barentries = new ArrayList<BarEntry>();
        barentries.add(new BarEntry(1, NoTeachers));
        barentries.add(new BarEntry(2, NoStudents));
        barentries.add(new BarEntry(3, NoCourses));
        BarDataSet bardataset = new BarDataSet(barentries, "Data");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<String> labels = new ArrayList();
        labels.add("");
        labels.add("Registered Teachers");
        labels.add("Registered Students");
        labels.add("No. of Courses");
        XAxis xAxis = barchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        BarData data = new BarData(bardataset);
        data.setBarWidth(0.9f);
        barchart.setData(data);

        //dl = (DrawerLayout)findViewById(R.id.stats);
       // t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);

       // dl.setDrawerListener(t);
       // t.syncState();

        BridgeGCTable gcTable = new BridgeGCTable(this);
        gcTable.create();
        gcTable.close();
        CriteriaTable criteriaTable = new CriteriaTable(this);
        criteriaTable.create();
        criteriaTable.close();
        QuestionTable questionTable = new QuestionTable(this);
        questionTable.create();
        questionTable.close();
        GradingLevelTable grade_table = new GradingLevelTable(this);
        grade_table.create();
        grade_table.close();
        LabTable lab_table = new LabTable(this);
        lab_table.create();
        lab_table.close();
        QuestionTable question_table = new QuestionTable(this);
        question_table.create();
        question_table.close();
        RubricCLOTable rubricCLOTable = new RubricCLOTable(this);
        rubricCLOTable.create();
        rubricCLOTable.close();
        RubricTable rubricTable = new RubricTable(this);
        rubricTable.create();
        rubricTable.close();
        StudentMarksTable marksTable = new StudentMarksTable(this);
        marksTable.create();
        marksTable.close();
      
        NavigationView navigationView = findViewById(R.id.nav_teacher_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped

                        switch (menuItem.getItemId()){
                            case R.id.nav_lab_eval:
                                btnLabEvaluationClicked();
                                break;
                            case R.id.nav_lab_report:
                                btnLabReportingClicked();
                                break;
                            case R.id.nav_lab_create:
                                btnLabCreateClicked();
                                break;
                            case R.id.nav_rubric:
                                btnRubricClicked();
                                break;
                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item:
                Intent j = new Intent(this,Login.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logIn(View v)
    {
        Intent i = new Intent(this,StudentSignUp.class);
        startActivity(i);
    }

    public void teacherRegistration(View v)
    {
        Intent i = new Intent(this,TeacherSignUp.class);
        startActivity(i);
    }
    public void adminSignUp(View v)
    {
        Intent i = new Intent(this,AdminSignUp.class);
        startActivity(i);
    }


    public  void btnRubricsDefinitionClicked(){
        Intent intent = new Intent(this, NewRubricActivity.class);
        startActivity(intent);
    }

    public  void btnLabCreationClicked(View view){

    }

    public  void btnLabEvaluationClicked(){
        Intent intent = new Intent(this, LabEvaluationActivity.class);
        intent.putExtra("ActivityName","LabEvaluation");
        intent.putExtra("TeacherId", teacherEmail);
        startActivity(intent);
    }

    public  void btnLabCreateClicked(){
        Intent intent = new Intent(this, LabEvaluationActivity.class);
        intent.putExtra("ActivityName","LabCreation");
        intent.putExtra("TeacherId", teacherEmail);
        startActivity(intent);
    }
    public  void btnRubricClicked(){

        Intent intent = new Intent(this, LabEvaluationActivity.class);
        intent.putExtra("TeacherId", teacherEmail);
        intent.putExtra("ActivityName","Rubrics");
        startActivity(intent);
    }
    public  void btnLabReportingClicked(){
        Intent intent = new Intent(this, LabEvaluationActivity.class);
        intent.putExtra("ActivityName","LabReporting");
        intent.putExtra("TeacherId", teacherEmail);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog);
        } else {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                          builder = new AlertDialog.Builder(this);
        }
        //final Intent j = new Intent(this, Stats.class);
        final Intent j = new Intent(this, Login.class);
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to logout")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(j);
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
}
