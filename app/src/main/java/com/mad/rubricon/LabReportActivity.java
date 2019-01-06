package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LabReportActivity extends AppCompatActivity {
    String CourseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_report);
        CourseId = getIntent().getStringExtra("crsId");
    }

    public  void OnGenerateReportClicked(View view){
        Intent intent = new Intent(this, LabReportViewActivity.class);
        intent.putExtra("crsId",CourseId);
        startActivity(intent);
    }
}
