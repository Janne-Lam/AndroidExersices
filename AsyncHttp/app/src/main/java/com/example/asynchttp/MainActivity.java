package com.example.asynchttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements HttpFetcher.ReporterInterface{

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnPressed(View view){
        editText = findViewById(R.id.editText);
        String text = editText.getText().toString();
        HttpFetcher fetcher = new HttpFetcher(new HttpFetcher.ReporterInterface(){
            @Override
            public void networkFetchDone(final String data){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView = findViewById(R.id.textView);
                        Log.d("TAG", "networkFetchDone: " + data);
                        textView.setText(data);
                    }
                });
            }
        });
        fetcher.execute(text);
    }

    @Override
    public void networkFetchDone(String data) {
    }
}
