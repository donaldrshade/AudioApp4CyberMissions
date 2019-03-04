package com.lightsys.audioapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;
import android.content.Context;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    File fileDir;



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
