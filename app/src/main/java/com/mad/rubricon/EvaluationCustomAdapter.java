package com.mad.rubricon;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EvaluationCustomAdapter  extends BaseAdapter implements ListAdapter {
    private ArrayList<CourseMarks> stdMarksList = new ArrayList<CourseMarks>();
    private Context context;

    public  EvaluationCustomAdapter(ArrayList<CourseMarks> marksList, Context c){
        stdMarksList = marksList;
        context = c;
    }

    @Override
    public int getCount() {
        return stdMarksList.size();
    }

    @Override
    public Object getItem(int position) {
        return stdMarksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return stdMarksList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_list_enter_marks, null);
        }

        TextView registraionNumberView = (TextView) view.findViewById(R.id.txtViewRegistrationNumber);
        EditText marksEditView = (EditText) view.findViewById(R.id.editTextMarks);

        registraionNumberView.setText(stdMarksList.get(position).getStudentRegistration());
        marksEditView.setText(Float.toString(stdMarksList.get(position).getMarks()));

        return view;
    }
}
