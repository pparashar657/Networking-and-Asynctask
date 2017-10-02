
package com.example.pawan.networkingandasynctext;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostAsyncTask.PostsDownloadListener{

    ArrayList<Posts> postses=new ArrayList<>();
    ArrayList<String> tilesposts = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter;
    ListView listView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"hello",Snackbar.LENGTH_LONG).show();
               fetchposts();
            }
        });
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        listView=(ListView)findViewById(R.id.list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i1=new Intent(MainActivity.this,CommentsActivity.class);
                i1.putExtra("userId",postses.get(i).getId());
                startActivity(i1);
                return true;
            }
        });
        stringArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,tilesposts);
        listView.setAdapter(stringArrayAdapter);

    }

    private void fetchposts() {
        String urlPost="https://jsonplaceholder.typicode.com/posts";
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        PostAsyncTask postAsyncTask = new PostAsyncTask(this);
        postAsyncTask.execute(urlPost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postsDownloaded(ArrayList<Posts> posts) {
        tilesposts.clear();
        if(posts!=null) {
            postses=posts;
            for (int i = 0; i < posts.size(); i++) {
                tilesposts.add(posts.get(i).getTitle());
            }
        }
        else{
            Toast.makeText(this, "NULLLLL", Toast.LENGTH_SHORT).show();
        }
        stringArrayAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

}
