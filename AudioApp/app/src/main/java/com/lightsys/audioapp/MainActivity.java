package com.lightsys.audioapp;

import java.io.InputStream;
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
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    File fileDir;
    ArrayList Courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getCourseData();
        initRecyclerView();
    }

    private void getCourseData() {
        Courses = new ArrayList<Course>();//init the Array List

        //This section grabs the data from the directory file.
        String input = "";
        InputStream temp = getApplicationContext().getResources().openRawResource(R.raw.file_dir);
        try{
            Scanner readFile = new Scanner(temp);
            while(readFile.hasNextLine()){
                input += readFile.nextLine();
            }
        }
        catch(Exception e){

        }
        //Now we populate the Courses object
        while(input.indexOf("<course>")>=0){//look for a course
            String courseSubstring = input.substring(input.indexOf("<course>"),input.indexOf("</course"));//isolate the course
            Course newCourse = new Course(courseSubstring.substring(courseSubstring.indexOf("name:")+5,courseSubstring.indexOf(";")));
            while(courseSubstring.indexOf("<lesson>")>=0){//look for a lesson
                Lesson newLesson = new Lesson();//make new lesson
                String lessonSubstring = input.substring(input.indexOf("<lesson>"),input.indexOf("</lesson"));
                if(lessonSubstring.indexOf("name:")>=0){
                    int startPoint = lessonSubstring.indexOf("name:")+5;
                    newLesson.setName(lessonSubstring.substring(startPoint,lessonSubstring.indexOf(";",startPoint)));
                }
                if(lessonSubstring.indexOf("mp3:")>=0){
                    int startPoint = lessonSubstring.indexOf("mp3:")+4;
                    newLesson.setMp3(lessonSubstring.substring(startPoint,lessonSubstring.indexOf(";",startPoint)));
                }
                if(lessonSubstring.indexOf("text:")>=0){
                    int startPoint = lessonSubstring.indexOf("text:")+5;
                    newLesson.setTextData(lessonSubstring.substring(startPoint,lessonSubstring.indexOf(";",startPoint)));
                }
                courseSubstring = courseSubstring.substring(lessonSubstring.length());
                newCourse.addLesson(newLesson);//put lesson in course
            }
            Courses.add(newCourse);//add the course to courses
            //refind the course
            courseSubstring = input.substring(input.indexOf("<course>"),input.indexOf("</course")+9);//isolate the course
            //remove it from the other screen

            input = input.substring(courseSubstring.length());
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(Courses, this);
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
