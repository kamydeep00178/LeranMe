package com.example.learnme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.learnme.ui.DataModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
    }

    public CustomAdapter(@NonNull Context context, ArrayList<DataModel> data) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.title_Name:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }
    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.title_Name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.title_dec);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtName.setOnClickListener(this);
        viewHolder.txtName.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
