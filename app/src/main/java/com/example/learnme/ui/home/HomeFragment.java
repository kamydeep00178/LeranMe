package com.example.learnme.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.learnme.CustomAdapter;
import com.example.learnme.R;
import com.example.learnme.databinding.FragmentHomeBinding;
import com.example.learnme.ui.DataModel;
import com.google.android.material.snackbar.Snackbar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

 //   private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
       /* homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/


        listView=(ListView)root.findViewById(R.id.list);

        dataModels= new ArrayList<>();

        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
        dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2","February 9, 2009"));
        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3","April 27, 2009"));
        dataModels.add(new DataModel("Donut","Android 1.6","4","September 15, 2009"));
        dataModels.add(new DataModel("Eclair", "Android 2.0", "5","October 26, 2009"));
        dataModels.add(new DataModel("Froyo", "Android 2.2", "8","May 20, 2010"));
        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9","December 6, 2010"));
        dataModels.add(new DataModel("Honeycomb","Android 3.0","11","February 22, 2011"));
        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14","October 18, 2011"));
        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16","July 9, 2012"));
        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19","October 31, 2013"));
        dataModels.add(new DataModel("Lollipop","Android 5.0","21","November 12, 2014"));
        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23","October 5, 2015"));

        adapter= new CustomAdapter(getActivity().getApplicationContext(),dataModels);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        try {
            URL url = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");


            InputStream is = getActivity().getAssets().open("example.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(new InputSource(url.openStream()));

           // Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList1 = doc.getElementsByTagName("item");
            Node node1 = nList1.item(0);
            Element element3 = (Element) node1;

            Log.e("TAG", "onCreateView: nList1"+element3 );
            NodeList nList = doc.getElementsByTagName("item");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    Log.e("TAG", "onCreateView: "+getValue("pubDate", element2) );
                /*    tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    tv1.setText(tv1.getText()+"Salary : " + getValue("salary", element2)+"\n");
                    tv1.setText(tv1.getText()+"-----------------------");*/
                }
            }//end of for loop
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }


        return root;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);

        return node.getNodeValue();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}