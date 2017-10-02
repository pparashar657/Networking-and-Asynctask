package com.example.pawan.networkingandasynctext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity implements CommentAsyncTask.CommentsDownloadListener {

    ArrayList<Comments> commentses=new ArrayList<>();
    ArrayList<String> tilescomments=new ArrayList<>();
    ArrayAdapter<String> commentsadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent=getIntent();
        int Id=intent.getIntExtra("userId",-1);
        if(Id==-1){
            Toast.makeText(this, "wrong hello", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, Id+"", Toast.LENGTH_SHORT).show();
        ListView listView1=(ListView)findViewById(R.id.commentslist);
        fetchComments(Id);
        commentsadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,tilescomments);
        listView1.setAdapter(commentsadapter);
    }

    private void fetchComments(int userId) {
        String urlPost="https://jsonplaceholder.typicode.com/posts/"+userId+"/comments";
       CommentAsyncTask commentAsyncTask=new CommentAsyncTask(this);
        commentAsyncTask.execute(urlPost);
    }

    @Override
    public void CommentsDownloaded(ArrayList<Comments> comments) {
        tilescomments.clear();
        if(comments!=null) {
            commentses=comments;
            for (int i = 0; i < comments.size(); i++) {
                tilescomments.add(comments.get(i).getBody());
            }
        }
        else{
            Toast.makeText(this, "NULLLLL", Toast.LENGTH_SHORT).show();
        }
        commentsadapter.notifyDataSetChanged();
    }

}
