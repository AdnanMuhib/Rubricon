package com.mad.rubricon;

import android.content.Context;
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

public class DescriptionsActivity extends AppCompatActivity {

    ListView desList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions);

        desList = findViewById(R.id.subCategoryList);
        descriptionAdapter adapter = new descriptionAdapter(this,R.layout.category_item, 3);
        desList.setAdapter(adapter);
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
                view = layoutInflater.inflate(R.layout.category_item, null);
            }

            TextView level_ = (TextView) view.findViewById(R.id.levelDes);
            EditText iconview = (EditText) view.findViewById(R.id.Des);

            level_.setText("Level " + (position+1));


            return view;
        }


    }
}
