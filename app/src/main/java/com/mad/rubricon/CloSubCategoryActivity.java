package com.mad.rubricon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class CloSubCategoryActivity extends AppCompatActivity {

    Spinner spinner;
    ListView subCategory;
    ArrayAdapter<String> adapter;

    String [] values={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clo_sub_category);

        subCategory = findViewById(R.id.subCategoryList);

        // List adapter...
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        subCategory.setAdapter(adapter);

        // Spinner adapter...
        spinner = findViewById(R.id.spCLOID);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.clos));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar_courses);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("CLO");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }

    @Override
    protected void onResume() {
        super.onResume();
        values = new String[RubricCLO.rubricCLO.criteriaArrayList.size()];
        int i = 0;
        for (Criteria criteria:RubricCLO.rubricCLO.criteriaArrayList){
            values[i] = "Criteria " + (i+1) + ":    " + criteria.getTitle();
            i++;
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        subCategory.setAdapter(adapter);
        subCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CloSubCategoryActivity.this);
                builder.setTitle("What you want to do?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RubricCLO.rubricCLO.criteriaArrayList.remove(i);
                        recreate();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
    }

    public void saveRubricClo(View view){
        Spinner spinner = findViewById(R.id.spCLOID);
        if (RubricCLO.rubricCLO.criteriaArrayList.size() == 0){
            Toast.makeText(this,"No Criteria added",Toast.LENGTH_SHORT).show();
        }
        else {
            RubricCLO.rubricCLO.cloID = Integer.parseInt(spinner.getSelectedItem().toString());
            RubricCLO.rubricCLO.rubricID = Rubric.rubric.id;
            Rubric.rubric.addRubricClo(RubricCLO.rubricCLO);
            RubricCLO.refresh();
            finish();
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

    public void addsubCategory(View view){
        startActivity(new Intent(this,DescriptionsActivity.class));
    }
}
