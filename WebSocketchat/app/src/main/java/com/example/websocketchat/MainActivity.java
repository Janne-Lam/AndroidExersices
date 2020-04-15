package com.example.websocketchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements EchoClientInterface {

    EchoClient echoClient = null;
    TextView messageView, statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openConnection();
    }

    public void btnSend(View v){
        if(echoClient != null && echoClient.isOpen()){
            EditText editText = findViewById(R.id.eT);
            String text = editText.getText().toString();
            echoClient.send(text);
            editText.setText("");
        }
    }

    private void openConnection(){
        try {
            echoClient = new EchoClient(new URI("wss://obscure-waters-98157.herokuapp.com"), MainActivity.this);
            echoClient.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void message(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageView = findViewById(R.id.messageView);
                messageView.append(message + "\n");
            }
        });
    }

    @Override
    public void status(final String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusView = findViewById(R.id.status);
                statusView.setText(status);
            }
        });
    }
}
