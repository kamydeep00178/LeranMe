package com.example.learnme.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.learnme.R;
import com.example.learnme.database.DataModel;
import com.example.learnme.database.DatabaseClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NewsFeedCustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private final List<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView btn_fav;
    }

    public NewsFeedCustomAdapter(@NonNull Context context, List<DataModel> data) {
        super(context, R.layout.news_feed_row_item, data);
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
            case R.id.btn_fav:
                Snackbar.make(v, "Item is Added to Bookmark Successfully", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                SaveTask st = new SaveTask();
                st.execute(dataModel);
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
            convertView = inflater.inflate(R.layout.news_feed_row_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.title_Name);
            viewHolder.txtDate = convertView.findViewById(R.id.txtDate);
            viewHolder.btn_fav = convertView.findViewById(R.id.btn_fav);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getTitle());
        viewHolder.txtDate.setText(dataModel.getPubDate());

        viewHolder.btn_fav.setOnClickListener(this);
        viewHolder.btn_fav.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }


    class SaveTask extends AsyncTask<DataModel, Void, Void> {

        @Override
        protected Void doInBackground(DataModel... dataModel) {


            //adding to database
            DatabaseClient.getInstance(mContext).getAppDatabase()
                    .newsDao()
                    .insert(dataModel[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }




}
