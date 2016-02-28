package com.example.al.smashingnpr;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by Al Herrera on 2/26/2016.
 */

public class MainActivity extends Activity {

    ListView lstFeed1, lstFeed2, lstFeed3;
    TextView text1, text2, text3;
    TextView body1, body2, body3;
    Button backButton1, backButton2, backButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up tabs
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

        //Assign ui elements to variables
        lstFeed1 = (ListView) findViewById(R.id.lstFeed1);
        lstFeed2 = (ListView) findViewById(R.id.lstFeed2);
        lstFeed3 = (ListView) findViewById(R.id.lstFeed3);
        text1 = (TextView) findViewById(R.id.txtTitle1);
        text2 = (TextView) findViewById(R.id.txtTitle2);
        text3 = (TextView) findViewById(R.id.txtTitle3);
        body1 = (TextView) findViewById(R.id.txtBody1);
        body1.setMovementMethod(LinkMovementMethod.getInstance());
        body2 = (TextView) findViewById(R.id.txtBody2);
        body2.setMovementMethod(LinkMovementMethod.getInstance());
        body3 = (TextView) findViewById(R.id.txtBody3);
        body3.setMovementMethod(LinkMovementMethod.getInstance());
        backButton1 = (Button) findViewById(R.id.btnBack1);
        backButton2 = (Button) findViewById(R.id.btnBack2);
        backButton3 = (Button) findViewById(R.id.btnBack3);

        //Read Rss
        final ReadRss readRss=new ReadRss(this, lstFeed1, lstFeed2, lstFeed3);
        readRss.execute();

        // ListView Item Click Listener 1
        lstFeed1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Hide Listview and display Title, body, and back button
                lstFeed1.setVisibility(View.INVISIBLE);
                text1.setVisibility(View.VISIBLE);
                body1.setVisibility(View.VISIBLE);
                backButton1.setVisibility(View.VISIBLE);
                //Fill the title and body text
                String title = readRss.feedItems1.get(position).getTitle();
                String body = readRss.feedItems1.get(position).getDescription() +
                        "<br /><br /><a href=\"" + readRss.feedItems1.get(position).getLink() + "\">Link to Article</a>";
                text1.setText(title);
                body1.setText(Html.fromHtml(body));
            }
        });

        // ListView Item Click Listener 2
        lstFeed2.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Hide Listview and display Title, body, and back button
                lstFeed2.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.VISIBLE);
                body2.setVisibility(View.VISIBLE);
                backButton2.setVisibility(View.VISIBLE);
                //Fill the title and body text
                String title = readRss.feedItems2.get(position).getTitle();
                String body = readRss.feedItems2.get(position).getDescription() +
                        "<br /><br /><a href=\"" + readRss.feedItems2.get(position).getLink() + "\">Link to Article</a>";
                text2.setText(title);
                body2.setText(Html.fromHtml(body));
            }
        });

        // ListView Item Click Listener 3
        lstFeed3.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Hide Listview and display Title, body, and back button
                lstFeed3.setVisibility(View.INVISIBLE);
                text3.setVisibility(View.VISIBLE);
                body3.setVisibility(View.VISIBLE);
                backButton3.setVisibility(View.VISIBLE);
                //Fill the title and body text
                String title = readRss.feedItems3.get(position).getTitle();
                String body = readRss.feedItems3.get(position).getDescription() +
                        "<br /><br /><a href=\"" + readRss.feedItems3.get(position).getLink() + "\">Link to Article</a>";
                text3.setText(title);
                body3.setText(Html.fromHtml(body));
            }
        });
    }

    public void btnBack1Click(View view) {
        //Return to Listview
        lstFeed1.setVisibility(View.VISIBLE);
        text1.setVisibility(View.GONE);
        body1.setVisibility(View.GONE);
        backButton1.setVisibility(View.GONE);
    }

    public void btnBack2Click(View view) {
        //Return to Listview
        lstFeed2.setVisibility(View.VISIBLE);
        text2.setVisibility(View.GONE);
        body2.setVisibility(View.GONE);
        backButton2.setVisibility(View.GONE);
    }

    public void btnBack3Click(View view) {
        //Return to Listview
        lstFeed3.setVisibility(View.VISIBLE);
        text3.setVisibility(View.GONE);
        body3.setVisibility(View.GONE);
        backButton3.setVisibility(View.GONE);
    }

}
