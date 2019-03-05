package com.lightsys.audioapp;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;

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
        getCourseData();
        initRecyclerView();
    }

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lessonActivity();
            }
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
        DatabaseConnection db = new DatabaseConnection(this.getApplicationContext());
        //Now we populate the Courses object
        while(input.indexOf("<course>")>=0){//look for a course
            String courseSubstring = input.substring(input.indexOf("<course>")+8,input.indexOf("</course>")).trim();//isolate the course
            Course newCourse = new Course(courseSubstring.substring(courseSubstring.indexOf("name:")+5,courseSubstring.indexOf(";")));
            courseSubstring = courseSubstring.substring(newCourse.getName().length()+5).trim();
            while(courseSubstring.indexOf("<lesson>")>=0){//look for a lesson
                Lesson newLesson = new Lesson();//make new lesson
                newLesson.setCourse(newCourse.getName());
                String lessonSubstring = courseSubstring.substring(courseSubstring.indexOf("<lesson>")+8,courseSubstring.indexOf("</lesson>"));
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
                db.addLesson(newLesson);
                courseSubstring = courseSubstring.substring(courseSubstring.indexOf("</lesson>")+9).trim();
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


    //Go to lesson activity
    //TODO: Remove this once RecyclerView is implemented
    private void lessonActivity() {
        Intent lesson = new Intent(MainActivity.this, lessonActivity.class);
        startActivity(lesson);
    }

    //Open menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Menu item actions
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
        else if(id == R.id.action_about){
            Intent about = new Intent(this, About.class);
            startActivity(about);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
