package com.mad.rubricon;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

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

//        for(int i=0; i<rubrics.getCount(); i++){
//            View itemView = (View) rubrics.getChildAt(i);
//            TextView item = itemView.findViewById(android.R.id.text1);
//            item.setTextColor(Color.BLACK);
//        }


        Toolbar toolbar = findViewById(R.id.toolbar_lab_eval);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Back");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
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

    public void createRubric(View view){
        final String m_Text = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select levels");


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("one");
        spinnerArray.add("two");
        spinnerArray.add("three");
        spinnerArray.add("four");
        spinnerArray.add("five");

        final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        builder.setView(spinner);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selected = spinner.getSelectedItem().toString();
                Intent intent = new Intent(getBaseContext(),NewRubricActivity.class);
                intent.putExtra("levels" ,selected);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void btnLAB(View v) {
        startActivity(new Intent(this,LabActivity.class));
    }
}
