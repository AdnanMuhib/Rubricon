package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AddCloActivity extends AppCompatActivity {

    ListView addClo;
    String [] CLOs = {};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clo);

        addClo =findViewById(R.id.cloList);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, CLOs);
        addClo.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar_lab_eval);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Back");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CLOs = new String[Rubric.rubric.rubricCLOs.size()];
        int i = 0;
        for (RubricCLO clo: Rubric.rubric.rubricCLOs){
            CLOs[i] = "CLO " + clo.cloID;
            i++;
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, CLOs);
        addClo.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        finish();
        Rubric.rubric.saveClos(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Rubric.rubric.saveClos(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AddClo(View view){
        startActivity(new Intent(this,CloSubCategoryActivity.class));
    }

}
