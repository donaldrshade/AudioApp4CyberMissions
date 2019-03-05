package com.lightsys.audioapp;

import java.util.ArrayList;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import android.content.Context;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    File fileDir;



    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<Course> mCourses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      
      /* Changed Where the files go, this would be the external location.
        //Finds sharedPreferences to gather data from
        sharedPreferences = this.getSharedPreferences("audioApp", Context.MODE_PRIVATE);
        //hasInited will figure weather or not the app has been run before.
        Boolean hasInited = sharedPreferences.getBoolean("sp_init",false);
        if(!hasInited){//only runs if the app has not been run before
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("sp_init",true);
            myEdit.commit();
            fileDir = getDir("Courses_Dir",Context.MODE_PRIVATE);//creates the fileDir for the app
        }
        */

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //create recycler view
        initRecyclerView();
    }
  
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recycler view");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(mCourses, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
  
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
            //Put Action Code here when they select it.
        }

        return super.onOptionsItemSelected(item);
    }
}
