package com.lightsys.audioapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.FormatException;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class lessonActivity extends AppCompatActivity {

    private View mContentView;          //Main page
    private View mContentControlsView;  //Bottom controls
    private View mMediaControlsView;    //Top (audio) controls



    //Media Buttons
    private MediaPlayer media = null;
    private ImageButton play;
    private ImageButton back10;
    private ImageButton fwd10;
    private ImageButton prev;
    private ImageButton next;
    private SeekBar seek;

    //Other Declarations
    private boolean mVisible;
    private boolean mIsPlaying = false;
    private Runnable mRunnable;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        mVisible = true;
        mContentControlsView = findViewById(R.id.content_controls);
        mMediaControlsView = findViewById(R.id.media_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        //Enable back button
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
                    mIsPlaying = false;
                } else {
                    media.start();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    mIsPlaying = true;
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
                seek.setProgress(media.getCurrentPosition());
            }
        });

        //Seek to 10 seconds ahead of current position
        fwd10 = findViewById(R.id.fwd10_button);
        fwd10.setImageDrawable(getResources().getDrawable(R.drawable.ic_fwd10));
        fwd10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.seekTo(media.getCurrentPosition() + 10000);
                seek.setProgress(media.getCurrentPosition());
            }
        });

        //Seek back to start of audio
        prev = findViewById(R.id.previous_button);
        prev.setImageDrawable(getResources().getDrawable(R.drawable.ic_prev));
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.seekTo(0);
                seek.setProgress(media.getCurrentPosition());
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
                seek.setProgress(media.getCurrentPosition());
            }
        });

        seek = findViewById(R.id.seek_bar);
        initSeekBar();
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    media.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(mIsPlaying){
                    media.pause();  //Only pause if currently playing
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mIsPlaying){
                    media.start();  //Only resume if previously playing
                }
            }
        });

        //When activity screen clicked, toggle visibility of controls
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVisible) {
                    mMediaControlsView.setVisibility(View.GONE);
                    mVisible = false;
                } else {
                    mMediaControlsView.setVisibility(View.VISIBLE);
                    mVisible = true;
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

    //Initialize SeekBar to auto-update position with audio
    protected void initSeekBar(){
        seek.setMax(media.getDuration());

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(media != null) {
                    seek.setProgress(media.getCurrentPosition());
                }
                mHandler.postDelayed(mRunnable, 500);
            }
        };
        mHandler.postDelayed(mRunnable, 500);
    }
    
    //Returns from lesson to MainActivity
    //and closes current lesson
    private void mainActivity() {
        Intent main = new Intent(lessonActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }

    //Open menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson, menu);
        return true;
    }

    //Menu item actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //TODO: Description
        if (id == R.id.action_notes) {
            Toast.makeText(this, "Take Some Good Notes", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO: Description
    @Override
    protected void onStop() {
        //TODO: Save stuff here?
        media.release();
        media = null;
        super.onStop();
    }
}
