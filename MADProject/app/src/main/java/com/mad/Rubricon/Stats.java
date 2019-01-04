package com.mad.Rubricon;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Stats extends AppCompatActivity {
    DBManager db;
    public DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout mDrawerLayout;
    BarChart barchart;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item:
                Intent j = new Intent(this,Login.class);
                //j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                return true;
            case R.id.item1:
                Intent i = new Intent(this,MainActivity.class);
                //j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
            {
                if(t.onOptionsItemSelected(item))
                    return true;

                return super.onOptionsItemSelected(item);
            }
                //return super.onOptionsItemSelected(item);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        try {
            db = new DBManager(this);
            db.open();
        } catch (Exception e) {
        }
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

        dl = (DrawerLayout)findViewById(R.id.stats);
        t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);

        dl.setDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.teacher:
                        Intent intent = new Intent(Stats.this,TeacherSignUp.class);
                        startActivity(intent);
                        break;
                    case R.id.student:
                        Intent i = new Intent(Stats.this,StudentSignUp.class);
                        startActivity(i);
                        break;
                    case R.id.admin:
                        Intent in = new Intent(Stats.this,AdminSignUp.class);
                        startActivity(in);
                        break;
                    default:
                        return true;
                }



                return true;
            }
        });


    }
    }

