package com.example.asynchronoussimplehttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    TextView textView, textView2;
    EditText editTextId, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addNameOrId(View view) {
        editTextId = findViewById(R.id.stockIdET);
        editTextName = findViewById(R.id.stockNameET);

        String id = editTextId.getText().toString();
        final String name = editTextName.getText().toString();

        Log.d("TAG", "addNameOrId: " + id);
        StockPriceFetcher fetcher = new StockPriceFetcher(new StockPriceFetcher.ReporterInterface() {
            @Override
            public void networkFetchDone(final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView2 = findViewById(R.id.stockTV2);
                        textView2.append(name + ": \n");
                        textView = findViewById(R.id.stockTV);
                        textView.append(data + "\n");
                    }
                });
            }
        });
        fetcher.execute(id);
    }
}
