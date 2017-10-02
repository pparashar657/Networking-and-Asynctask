package com.example.pawan.networkingandasynctext;

import android.os.AsyncTask;
import android.util.Log;

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

public class CommentAsyncTask extends AsyncTask<String,Void,ArrayList<Comments>> {
    public CommentAsyncTask.CommentsDownloadListener commentsDownloadListener;
    public CommentAsyncTask(CommentAsyncTask.CommentsDownloadListener Listener) {
        commentsDownloadListener=Listener;
    }

    @Override
    protected ArrayList<Comments> doInBackground(String... strings) {
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
            ArrayList<Comments> comments= parsecomments(response);
            return comments;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<Comments> parsecomments(String response) throws JSONException {
        ArrayList<Comments> comments=null;
        JSONArray rootarray=new JSONArray(response);
        if(rootarray!=null){
            comments=new ArrayList<>();
            for(int i=0;i<rootarray.length();i++){
                JSONObject object=rootarray.getJSONObject(i);
                int postId=object.getInt("postId");
                int id=object.getInt("id");
                String name=object.getString("name");
                String body=object.getString("body");
                String email=object.getString("email");
                Comments comments1=new Comments(postId,id,name,email,body);
                comments.add(comments1);
            }
        }
        else {
            Log.i("ROOTARRAYNULLL","root is null");
        }
        return comments;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Comments> comments) {
        commentsDownloadListener.CommentsDownloaded(comments);
    }

    public static interface CommentsDownloadListener{
        void CommentsDownloaded(ArrayList<Comments> comments);
    }

}
