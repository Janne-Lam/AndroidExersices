package com.example.httpvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnPressed(View view) {
        editText = findViewById(R.id.editText);
        String text = editText.getText().toString();
        HttpFetcher fetcher = new HttpFetcher(new HttpFetcher.ReporterInterface() {
            @Override
            public void notifySuccess(String requestType) {
                textView = findViewById(R.id.textView);
                textView.setText(requestType);
            }

            @Override
            public void notifyError(String requestType) {
                textView = findViewById(R.id.textView);
                textView.setText(requestType);
            }
        }, MainActivity.this);
        fetcher.execute(text);
    }
}
