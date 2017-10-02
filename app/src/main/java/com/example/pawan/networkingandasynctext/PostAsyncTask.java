package com.example.pawan.networkingandasynctext;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Pawan on 01-10-2017.
 */

public class PostAsyncTask extends AsyncTask<String,Void,ArrayList<Posts>> {

    public PostsDownloadListener postsDownloadListener;
    public PostAsyncTask(PostsDownloadListener Listener) {
        postsDownloadListener=Listener;
    }

    @Override
    protected ArrayList<Posts> doInBackground(String... strings) {
        String urlpost=strings[0];
        try {
            URL url =new URL(urlpost);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();
            Scanner scanner= new Scanner(inputStream);
            String response="";
            while(scanner.hasNext()){
                response+=scanner.next();
            }
            Log.i("Response",response);
            ArrayList<Posts> posts= parseposts(response);
            return posts;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<Posts> parseposts(String response) throws JSONException {
       ArrayList<Posts> posts=null;
        JSONArray rootarray=new JSONArray(response);
        if(rootarray!=null){
            posts=new ArrayList<>();
            for(int i=0;i<rootarray.length();i++){
                JSONObject object=rootarray.getJSONObject(i);
                int userid=object.getInt("userId");
                int id=object.getInt("id");
                String title=object.getString("title");
                String body=object.getString("body");
                Posts post=new Posts(userid,id,title,body);
                posts.add(post);
            }
        }
        else {
            Log.i("ROOTARRAYNULLL","root is null");
        }
        return posts;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Posts> posts) {
      postsDownloadListener.postsDownloaded(posts);
    }

    public static interface PostsDownloadListener{
      void postsDownloaded(ArrayList<Posts> posts);
    }
}
