package com.mad.rubricon;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RubricsActivity extends AppCompatActivity {

    ListView rubrics;
    String[] values = {"Rubric 1", "Rubric 2"};
    FloatingActionButton newRubric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubrics);

        rubrics = findViewById(R.id.rubricsList);
        newRubric = findViewById(R.id.newRubric);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        rubrics.setAdapter(adapter);
    }

    public void createRubric(View view){
        startActivity(new Intent(this,NewRubricActivity.class));
    }
}
