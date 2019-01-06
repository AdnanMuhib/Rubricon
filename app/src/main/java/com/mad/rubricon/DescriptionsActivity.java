package com.mad.rubricon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DescriptionsActivity extends AppCompatActivity {

    ListView desList;
    EditText title;
    EditText description;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions);

        title = findViewById(R.id.etCriteriaTitle);
        description = findViewById(R.id.etDescription);

        desList = findViewById(R.id.subCategoryList);
        level = Rubric.rubric.gradingLevels.size();
        descriptionAdapter adapter = new descriptionAdapter(this,R.layout.category_item, level);
        desList.setAdapter(adapter);

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

    public void saveCriteria(View view){
        String criteriaTitle = title.getText().toString();
        String des = description.getText().toString();

        if (criteriaTitle.equals("")){
            title.setError("Title not entered!");
            return;
        }
        else if (des.equals("")){
            description.setError("Description not entered");
            return;
        }
        else {
            List<String> descList = new ArrayList<>();
            for (int i =0; i<level; i++){
                View item = desList.getChildAt(i);
                EditText etDesc = item.findViewById(R.id.etLevelDescription);
                String desc = etDesc.getText().toString().trim();
                if(desc.equals("")) {
                    etDesc.setError("Level Description not entered!");
                    return;
                }
                descList.add(desc);
            }

            Criteria.criteria.setDescription(des);
            Criteria.criteria.setTitle(criteriaTitle);


            int i = 1;
            for (String marks:descList){
                Rubric.rubric.addGradingLevel(new GradingLevel("Level "+i,Integer.parseInt(marks)));
                i++;
            }
        }
    }

    public class descriptionAdapter extends ArrayAdapter {
        int levels;
        private Context context;
        public descriptionAdapter(@NonNull Context context, int resource, int levels) {
            super(context, resource,levels);
            this.levels = levels;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this.levels;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (null == view) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.level_item_description, null);
            }

            TextView level = (TextView) view.findViewById(R.id.levelNumber);

            level.setText("Level " + (position+1));
            return view;
        }


    }
}
