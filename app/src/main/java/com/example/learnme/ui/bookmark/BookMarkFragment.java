package com.example.learnme.ui.bookmark;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.learnme.R;
import com.example.learnme.database.DataModel;
import com.example.learnme.database.DatabaseClient;
import com.example.learnme.databinding.FragmentBookmarksBinding;

import java.util.ArrayList;
import java.util.List;

/* BookMark items Show*/
public class BookMarkFragment extends Fragment {

    //View Binding
    private FragmentBookmarksBinding binding;

    //Show the item list
    ListView listView;

    ArrayList<DataModel> dataModels;

    //Custom Adapter for items
    private static BookMarkCustomAdapter adapter;
    TextView text_no;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentBookmarksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.list);
        text_no= root.findViewById(R.id.text_no);

        dataModels = new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel = dataModels.get(position);
                RemoveTask gt = new RemoveTask();
                gt.execute(dataModel);

                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

                Toast.makeText(getContext(),dataModel.getTitle()+"is Remove Successfully",
                        Toast.LENGTH_SHORT).show();

                if(dataModels.size()==0)
                {
                    text_no.setVisibility(View.VISIBLE);
                }
                else {
                    text_no.setVisibility(View.GONE);
                }


            }
        });




        getTasks();
        return root;
    }

    // Get Data from database show bookmarks used async task for background operation
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<DataModel>> {

            @Override
            protected List<DataModel> doInBackground(Void... voids) {
                List<DataModel> taskList = DatabaseClient
                        .getInstance(getActivity().getApplicationContext())
                        .getAppDatabase()
                        .newsDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<DataModel> tasks) {
                super.onPostExecute(tasks);
                dataModels = (ArrayList<DataModel>) tasks;
                adapter = new BookMarkCustomAdapter(getActivity().getApplicationContext(), tasks);
                listView.setAdapter(adapter);
                if(tasks.size()==0)
                {
                    text_no.setVisibility(View.VISIBLE);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class RemoveTask extends AsyncTask<DataModel, Void, Void> {

        @Override
        protected Void doInBackground(DataModel... dataModel) {


            //adding to database
            DatabaseClient.getInstance(getContext()).getAppDatabase()
                    .newsDao()
                    .delete(dataModel[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}