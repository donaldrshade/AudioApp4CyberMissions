package com.lightsys.audioapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import android.media.MediaPlayer;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class lessonActivity extends AppCompatActivity {

    private View mContentView;
    private View mContentControlsView;
    private View mMediaControlsView;

    private boolean mVisible;


    private MediaPlayer media = null;
    private ImageButton play;
    private ImageButton back10;
    private ImageButton fwd10;
    private ImageButton prev;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);

        mVisible = true;
        mContentControlsView = findViewById(R.id.content_controls);
        mMediaControlsView = findViewById(R.id.media_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        String uri = "l1_born_again.mp3";
        media = createMedia(uri);

        play = findViewById(R.id.play_button);
        play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        play.setOnClickListener(new View.OnClickListener() {
            //If audio is playing, clicking this pauses it
            //Otherwise, play audio
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

        back10 = findViewById(R.id.back10_button);
        back10.setImageDrawable(getResources().getDrawable(R.drawable.ic_back10));
        back10.setOnClickListener(new View.OnClickListener() {
            //Seek to 10 seconds behind current position
            @Override
            public void onClick(View view) {
                media.seekTo(media.getCurrentPosition() - 10000);
            }
        });

        fwd10 = findViewById(R.id.fwd10_button);
        fwd10.setImageDrawable(getResources().getDrawable(R.drawable.ic_fwd10));
        fwd10.setOnClickListener(new View.OnClickListener() {
            //Seek to 10 seconds ahead of current position
            @Override
            public void onClick(View view) {
                media.seekTo(media.getCurrentPosition() + 10000);
            }
        });

        prev = findViewById(R.id.previous_button);
        prev.setImageDrawable(getResources().getDrawable(R.drawable.ic_prev));
        prev.setOnClickListener(new View.OnClickListener() {
            //Seek back to start of audio
            @Override
            public void onClick(View view) {
                media.seekTo(0);
            }
        });

        next = findViewById(R.id.next_button);
        next.setImageDrawable(getResources().getDrawable(R.drawable.ic_next));
        next.setOnClickListener(new View.OnClickListener() {
            //Seek to end of audio
            @Override
            public void onClick(View view) {
                media.seekTo(media.getDuration());
            }
        });

        FloatingActionButton home = findViewById(R.id.home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.release();
                media = null;

                mainActivity();
            }
        });

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
    }

    private MediaPlayer createMedia(String url){
        MediaPlayer mediaPlayer = MediaPlayer.create(lessonActivity.this, R.raw.l1_born_again);
        return mediaPlayer;
    }

    private void mainActivity() {
        Intent main = new Intent(lessonActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        mContentControlsView.setVisibility(View.GONE);
        mMediaControlsView.setVisibility(View.GONE);
        mVisible = false;
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentControlsView.setVisibility(View.VISIBLE);
        mMediaControlsView.setVisibility(View.VISIBLE);
        mVisible = true;
    }
}
