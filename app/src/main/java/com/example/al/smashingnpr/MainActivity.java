package com.example.al.smashingnpr;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ListView lstFeed1;
    ListView lstFeed2;
    ListView lstFeed3;
    TextView text1;
    TextView text2;
    TextView text3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Feed 1");
        tabSpec.setContent(R.id.tabFeed1);
        tabSpec.setIndicator("Feed 1");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Feed 2");
        tabSpec.setContent(R.id.tabFeed2);
        tabSpec.setIndicator("Feed 2");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Feed 3");
        tabSpec.setContent(R.id.tabFeed3);
        tabSpec.setIndicator("Feed 3");
        tabHost.addTab(tabSpec);

        lstFeed1 = (ListView) findViewById(R.id.lstFeed1);
        lstFeed2 = (ListView) findViewById(R.id.lstFeed2);
        lstFeed3 = (ListView) findViewById(R.id.lstFeed3);
        final TextView text1 = (TextView) findViewById(R.id.text1);
//        TextView text2;
//        TextView text3;

        //Read Rss
        final ReadRss readRss=new ReadRss(this, lstFeed1, lstFeed2, lstFeed3);
        readRss.execute();

        // ListView Item Click Listener
        lstFeed1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                String itemValue = (String) lstFeed1.getItemAtPosition(position);
                // Hide Listview
                lstFeed1.setVisibility(View.INVISIBLE);
                text1.setVisibility(View.VISIBLE);

                //This is the end finally
                String title = (String) readRss.feedItems1.get(position).getTitle();
                text1.setText(title);

                // Show Alert
//                Toast.makeText(getApplicationContext(),"Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG).show();
            }

        });

    }

}
