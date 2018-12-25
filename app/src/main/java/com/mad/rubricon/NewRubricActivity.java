package com.mad.rubricon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NewRubricActivity extends AppCompatActivity {

    ListView levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rubric);

        levels = findViewById(R.id.levelList);

        LeverAdapter leverAdapter = new LeverAdapter(this,R.layout.list_item, 3);

        levels.setAdapter(leverAdapter);

    }

    public void cancelActivity(View View){
        startActivity(new Intent(this,RubricsActivity.class));
    }

    public void saveRubric(View view){
        startActivity(new Intent(this,AddCloActivity.class));
    }

    public class LeverAdapter extends ArrayAdapter {
        int levels;
        private Context context;
        public LeverAdapter(@NonNull Context context, int resource, int levels) {
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
                view = layoutInflater.inflate(R.layout.list_item, null);
            }

            TextView level_ = (TextView) view.findViewById(R.id.level);
            EditText iconview = (EditText) view.findViewById(R.id.marks);

            level_.setText("Level " + (position+1));


            return view;
        }


    }
}
