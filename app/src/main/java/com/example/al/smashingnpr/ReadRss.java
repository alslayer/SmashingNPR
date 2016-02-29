package com.example.al.smashingnpr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Al on 2/26/2016.
 */
public class ReadRss extends AsyncTask<Void,Void,Void>{
    Context context;
    String addresss1="http://www.npr.org/rss/rss.php?id=1019";
    String addresss2="http://www.npr.org/rss/rss.php?id=1048";
    String addresss3="http://www.npr.org/rss/rss.php?id=1025";
    ProgressDialog progressDialog;
    URL url;
    ArrayList<FeedItem> feedItems1, feedItems2, feedItems3;
    ListView lstFeed1, lstFeed2, lstFeed3;
    int whichFeed;

    public ReadRss(Context context, ListView lstFeed1, ListView lstFeed2, ListView lstFeed3){
        this.lstFeed1=lstFeed1;
        this.lstFeed2=lstFeed2;
        this.lstFeed3=lstFeed3;
        this.context=context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        if (feedItems1 != null && feedItems2 != null && feedItems1 != null) {
            //Format and display in listview
            List<String> displayList1 = new ArrayList<>();
            for (int i = 0; i < feedItems1.size(); i++) {
                String title = feedItems1.get(i).getTitle();
                String pubDate = feedItems1.get(i).getPubDate();
                String combine = pubDate + " : " + title;
                System.out.println(combine);
                displayList1.add(combine);
            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, displayList1);
            lstFeed1.setAdapter(adapter1);
            List<String> displayList2 = new ArrayList<>();
            for (int i = 0; i < feedItems2.size(); i++) {
                String title = feedItems2.get(i).getTitle();
                String pubDate = feedItems2.get(i).getPubDate();
                String combine = pubDate + " : " + title;
                System.out.println(combine);
                displayList2.add(combine);
            }
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, displayList2);
            lstFeed2.setAdapter(adapter2);
            List<String> displayList3 = new ArrayList<>();
            for (int i = 0; i < feedItems3.size(); i++) {
                String title = feedItems3.get(i).getTitle();
                String pubDate = feedItems3.get(i).getPubDate();
                String combine = pubDate + " : " + title;
                System.out.println(combine);
                displayList3.add(combine);
            }
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, displayList3);
            lstFeed3.setAdapter(adapter3);
        } else {
            //Alert user that app could not load feeds.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Could not load feeds");
            alertDialogBuilder
                    .setMessage("Check your internet connection and restart app.")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // Do nothing
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Run for each feed
        whichFeed = 1;
        ProcessXML(Getdata(addresss1));
        whichFeed = 2;
        ProcessXML(Getdata(addresss2));
        whichFeed = 3;
        ProcessXML(Getdata(addresss3));
        return null;
    }

    private void ProcessXML(Document data) {
        //Determine which feed and fill it.
        if (data != null) {
            if (whichFeed == 1)
                feedItems1 = new ArrayList<>();
            if (whichFeed == 2)
                feedItems2 = new ArrayList<>();
            if (whichFeed == 3)
                feedItems3 = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node currentchild = items.item(i);
                if (currentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item = new FeedItem();
                    NodeList itemchilds = currentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")) {
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            item.setLink(current.getTextContent());
                        }
                    }
                    if (whichFeed == 1)
                        feedItems1.add(item);
                    if (whichFeed == 2)
                        feedItems2.add(item);
                    if (whichFeed == 3)
                        feedItems3.add(item);
                }
            }
        }
    }
    public Document Getdata(String address){
        try {
            url=new URL(address);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream=connection.getInputStream();
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
