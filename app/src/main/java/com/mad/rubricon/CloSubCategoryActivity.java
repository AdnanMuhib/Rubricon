package com.mad.rubricon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class CloSubCategoryActivity extends AppCompatActivity {

    Spinner spinner;
    ListView subCategory;
    String [] values = {"Sub-Category 1","Sub-Category 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clo_sub_category);

        subCategory = findViewById(R.id.subCategoryList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        spinner = findViewById(R.id.spCLOID);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.clos));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
    }

    public void addsubCategory(View view){
        startActivity(new Intent(this,DescriptionsActivity.class));
    }
}