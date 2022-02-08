package com.nire.threadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView progressText;
    EditText timer;
    Boolean inProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        progressText = findViewById(R.id.progressText);
        timer = findViewById(R.id.timer);

        progressText.setText("0");

        button.setOnClickListener((e) -> {
            if (inProgress){
                Toast.makeText(MainActivity.this, "Working timer", Toast.LENGTH_SHORT).show();
                return;
            }
            String timerText = timer.getText().toString();
            if (timerText.equals("")){
                Toast.makeText(MainActivity.this, "Please enter timer", Toast.LENGTH_SHORT).show();
                return;
            }
            new TimerThread().start();
        });
    }

    class TimerThread extends Thread{
        @Override
        public void run() {
            String timerText = timer.getText().toString();
            int timerValue = Integer.parseInt(timerText);
            synchronized (this) {
                inProgress = true;
                try {
                    for (int i = 1; i < (timerValue + 1)*4; i++) {
                        wait(250);
                        if (i % 3 == 1){
                            runOnUiThread(() -> progressText.setText("Loading."));
                        }
                        if (i % 3 == 2){
                            runOnUiThread(() -> progressText.setText("Loading.."));
                        }
                        if (i % 3 == 0){
                            runOnUiThread(() -> progressText.setText("Loading..."));
                        }
                    }
                    runOnUiThread(() -> progressText.setText("finish"));
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                inProgress = false;
            }
        }
    }
}