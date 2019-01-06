package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class LabReportActivity extends AppCompatActivity {
    String CourseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report);
        Toolbar toolbar = findViewById(R.id.toolbar_lab_report_activity);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Lab Report");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        CourseId = getIntent().getStringExtra("crsId");
    }

    public  void OnGenerateReportClicked(View view){
        Intent intent = new Intent(this, LabReportViewActivity.class);
        intent.putExtra("crsId",CourseId);
        startActivity(intent);
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
}
