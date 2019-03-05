package com.lightsys.audioapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.FormatException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class lessonActivity extends AppCompatActivity {

    private View mContentView;          //Main page
    private View mContentControlsView;  //Bottom controls
    private View mMediaControlsView;    //Top (audio) controls

    private boolean mVisible;

    //Media Buttons
    private MediaPlayer media = null;
    private ImageButton play;
    private ImageButton back10;
    private ImageButton fwd10;
    private ImageButton prev;
    private ImageButton next;

    //Content Buttons
    private FloatingActionButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        mVisible = true;
        mContentControlsView = findViewById(R.id.content_controls);
        mMediaControlsView = findViewById(R.id.media_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        //TODO: Get string from Script file thing
        String mp3 = "l1_born_again.mp3";
        try {
            media = createMedia(mp3);
        } catch (FormatException e) {
            Toast.makeText(getApplicationContext(), "Incorrect file format", Toast.LENGTH_SHORT).show();
            mainActivity();
        }

        //If audio is playing, clicking play button pauses it
        //Otherwise, play audio
        play = findViewById(R.id.play_button);
        play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(media.isPlaying()){
                    media.pause();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                } else {
                    media.start();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
            }
        });

        //Seek to 10 seconds behind current position
        back10 = findViewById(R.id.back10_button);
        back10.setImageDrawable(getResources().getDrawable(R.drawable.ic_back10));
        back10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.seekTo(media.getCurrentPosition() - 10000);
            }
        });

        //Seek to 10 seconds ahead of current position
        fwd10 = findViewById(R.id.fwd10_button);
        fwd10.setImageDrawable(getResources().getDrawable(R.drawable.ic_fwd10));
        fwd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.seekTo(media.getCurrentPosition() + 10000);
            }
        });

        //Seek back to start of audio
        prev = findViewById(R.id.previous_button);
        prev.setImageDrawable(getResources().getDrawable(R.drawable.ic_prev));
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.seekTo(0);
            }
        });

        //Seek to end of audio
        //TODO: When end of audio reached, prompt to load next lesson
        next = findViewById(R.id.next_button);
        next.setImageDrawable(getResources().getDrawable(R.drawable.ic_next));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.seekTo(media.getDuration());
            }
        });

        //TODO: Put this in menu rather than FAB
        home = findViewById(R.id.home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.release();
                media = null;

                mainActivity();
            }
        });

        //When activity screen clicked, toggle visibility of controls
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVisible) {
                    hide();
                } else {
                    show();
                }
            }
        });
    }

    //Creates and prepares media to be played
    //Throws FormatException if file is not an mp3
    private MediaPlayer createMedia(String mp3) throws FormatException {
        if(mp3.contains(".mp3")) {
            String file = mp3.replace(".mp3", "");
            int id = getResources().getIdentifier(file,"raw", getPackageName());
            MediaPlayer mediaPlayer = MediaPlayer.create(lessonActivity.this, id);
            return mediaPlayer;
        } else {
            throw new FormatException();
        }
    }
    
    //Returns from lessonActivity to MainActivity
    private void mainActivity() {
        Intent main = new Intent(lessonActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }

    //Hide controls
    private void hide() {
        mContentControlsView.setVisibility(View.GONE);
        mMediaControlsView.setVisibility(View.GONE);
        mVisible = false;
    }

    //Show controls
    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentControlsView.setVisibility(View.VISIBLE);
        mMediaControlsView.setVisibility(View.VISIBLE);
        mVisible = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notes) {
            //add code to do the switching here.
            Toast.makeText(this, "Take Some Good Notes", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
