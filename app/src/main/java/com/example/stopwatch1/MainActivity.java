package com.example.stopwatch1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView stopwatch;
    private Button startButton,stopButton, resetButton;
    private boolean isRunning;
    private long startTime, elapsedTime;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatch = findViewById(R.id.stopwatch);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();

        startButton.setOnClickListener(v -> {
            if (isRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });
        stopButton.setOnClickListener(v -> {
            if (isRunning) {
                stopTimer();
            }
        });

        resetButton.setOnClickListener(v -> resetTimer());
    }

    private void startTimer() {
        startTime = SystemClock.uptimeMillis() - elapsedTime;
        handler.postDelayed(updateTimerRunnable, 0);
        isRunning = true;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
    private void stopTimer() {
        handler.removeCallbacks(updateTimerRunnable);
        isRunning = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void resetTimer() {
        handler.removeCallbacks(updateTimerRunnable);
        stopwatch.setText("00:00:00");
        elapsedTime = 0;
        isRunning = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTime = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (elapsedTime % 1000);
            stopwatch.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 10);
        }
    };
}