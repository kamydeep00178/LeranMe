package com.example.learnme.ui.bookmark;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.learnme.R;
import com.example.learnme.database.DataModel;

import java.util.List;

/* Custom Adapter for BookMark */
public class BookMarkCustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{


    private final List<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDesc;
        TextView txtDate;
        TextView txtLink;
    }

    public BookMarkCustomAdapter(@NonNull Context context, List<DataModel> data) {
        super(context, R.layout.bookmark_row_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

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
            convertView = inflater.inflate(R.layout.bookmark_row_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.title_Name);
            viewHolder.txtDesc = convertView.findViewById(R.id.title_desc);
            viewHolder.txtDate = convertView.findViewById(R.id.title_date);
            viewHolder.txtLink = convertView.findViewById(R.id.title_link);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getTitle());
        viewHolder.txtDesc.setText(dataModel.getDescription());
        viewHolder.txtLink.setText(dataModel.getLink());
        viewHolder.txtDate.setText(dataModel.getPubDate());
        viewHolder.txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataModel.getLink()));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(browserIntent);
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }







}
