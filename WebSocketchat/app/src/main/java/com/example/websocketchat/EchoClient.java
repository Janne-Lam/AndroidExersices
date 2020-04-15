package com.example.websocketchat;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface EchoClientInterface{
    void message(String message);
    void status(String status);
}

public class EchoClient extends WebSocketClient{

    EchoClientInterface observer;

    private final static String TAG = "EchoClient";
    
    public EchoClient(URI serverUri, EchoClientInterface observer){
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "onOpen: ");
        observer.status("Open Connection");
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "onMessage: " + message);
        observer.message(message);
        observer.status("Received Message");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG, "onClose: ");
        observer.status("Socket Closed");
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, "onError: ");
        observer.status("Error: " + ex.toString());
    }
}
