package com.example.learnme.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.learnme.ui.NewsDetailActivity;
import com.example.learnme.R;
import com.example.learnme.databinding.FragmentHomeBinding;
import com.example.learnme.database.DataModel;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/* show the Latest News **/
public class HomeFragment extends Fragment {
    //View Binding
    private FragmentHomeBinding binding;

    ArrayList<DataModel> dataModels;

    //Show the item list
    ListView listView;

    //Custom Adapter for items
    private static NewsFeedCustomAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;



        listView = root.findViewById(R.id.list);

        dataModels = new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                DataModel dataModel = dataModels.get(position);

                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("title",dataModel.getTitle());
                intent.putExtra("desc",dataModel.getDescription());
                intent.putExtra("date",dataModel.getPubDate());
                intent.putExtra("link",dataModel.getLink());

                startActivity(intent);
                Toast.makeText(getContext(),dataModel.getTitle(),Toast.LENGTH_SHORT).show();


            }
        });


        new GetDataAsyncTask("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml").execute();

        return root;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getTextContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void callBackData(NodeList result) {
        for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;
                Log.e("TAG", "description: " + getValue("description", element2));
                Log.e("TAG", "title: " + getValue("title", element2));
                Log.e("TAG", "pubDate: " + getValue("link", element2));

                dataModels.add(
                        new DataModel(getValue("title", element2),
                        getValue("description", element2),
                        getValue("pubDate", element2),
                        getValue("link", element2))
                );
            }
        }

        adapter = new NewsFeedCustomAdapter(getActivity().getApplicationContext(), dataModels);

        listView.setAdapter(adapter);
    }

    //HTTP Calling and Get The Latest News and this operation is done on background thread
    public class GetDataAsyncTask extends AsyncTask<String[], Void, NodeList> {

        private final String url;

        public GetDataAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected NodeList doInBackground(String[]... params) {
            try {
                URL url1 = new URL(this.url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream stream = connection.getInputStream();

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url1.openStream()));
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("item");
                stream.close();

                return nList;

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AsyncTask", "exception");
                return null;
            }
        }


        @Override
        protected void onPostExecute(NodeList result) {
            //call back data to main thread
            callBackData(result);


        }
    }


}

