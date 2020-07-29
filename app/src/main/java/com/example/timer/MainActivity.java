package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int maxTimeInSeconds = 600;
    TextView textView;
    MediaPlayer mediaPlayer;
    CountDownTimer timer;
    Button button;
    boolean isGoing = false;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);

        seekBar.setMax(maxTimeInSeconds);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //seekBar.setProgress(progress);
                Log.i("seekBar value", String.valueOf(progress));
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void startTimer(View view){

       if(isGoing){
            button.setText("Go");
            isGoing = false;
            seekBar.setProgress(0);
            seekBar.setEnabled(true);
            textView.setText("00:00");
            timer.cancel();

        }else{

           timer = new CountDownTimer(seekBar.getProgress()*1000, 1000) {

               @Override
               public void onTick(long millisUntilFinished) {
                   int timeLeft = (int)(millisUntilFinished/1000);
                   updateTimer(timeLeft);
               }

               @Override
               public void onFinish() {
                   mediaPlayer.start();
               }
           }.start();
            button.setText("Stop!");
            isGoing = true;
            seekBar.setEnabled(false);
        }
    }
    public void updateTimer(int timeLeft){
        int minutesLeft = timeLeft / 60;
        int secondsLeft = timeLeft % 60;
        textView.setText(String.format("%02d", minutesLeft) + ":" + String.format("%02d", secondsLeft));
    }
}