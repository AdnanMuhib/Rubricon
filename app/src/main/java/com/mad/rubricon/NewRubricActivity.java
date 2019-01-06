package com.mad.rubricon;

import android.content.Context;
import android.content.Intent;
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

public class NewRubricActivity extends AppCompatActivity {

    ListView levels;
    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rubric);

        levels = findViewById(R.id.levelList);
        level = getLevel(getIntent().getStringExtra("levels"));
        LevelAdapter levelAdapter = new LevelAdapter(this,R.layout.level_item_marks, level);

        levels.setAdapter(levelAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar_lab_eval);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Rubrics");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
    }

    public int getLevel(String level){
        switch (level){
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
        }
        return 0;
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

    public void cancelActivity(View View){
        startActivity(new Intent(this,RubricsActivity.class));
    }

    public void saveRubric(View view){
        List<String> marksList = new ArrayList<>();
        for (int i =0; i<level; i++){
            View item = levels.getChildAt(i);
            EditText etMarks = item.findViewById(R.id.marks);
            String marks = etMarks.getText().toString().trim();
            if(marks.equals("")) {
                etMarks.setError("Please enter marks!");
                return;
            }
            marksList.add(marks);
        }
        int i = 1;
        for (String marks:marksList){
            Rubric.rubric.addGradingLevel(new GradingLevel("Level "+i,Integer.parseInt(marks)));
            i++;
        }
        Rubric.rubric.saveRubric(this);

        startActivity(new Intent(this,AddCloActivity.class));
    }

    public class LevelAdapter extends ArrayAdapter {
        int levels;
        private Context context;
        public LevelAdapter(@NonNull Context context, int resource, int levels) {
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
                view = layoutInflater.inflate(R.layout.level_item_marks, null);
            }

            TextView level_ = (TextView) view.findViewById(R.id.level);
            EditText iconview = (EditText) view.findViewById(R.id.marks);

            level_.setText("Level " + (position+1));


            return view;
        }
    }
}
