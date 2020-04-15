package com.example.lab1_gps_location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String lat;
    String longi;
    String userCountry;
    String userAddress;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***********Works well at mobile, failed to find location in emulator*********/

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        }
        else{
            final Geocoder geocoder = new Geocoder(this);
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        lat = String.valueOf(location.getLatitude());
                        longi = String.valueOf(location.getLongitude());

                        TextView textView = findViewById(R.id.textView);
                        textView.setText(lat + "  " + longi);

                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            TextView textView2 = findViewById(R.id.textView2);
                            if(addresses != null && addresses.size() > 0){
                                userCountry = addresses.get(0).getCountryName();
                                userAddress = addresses.get(0).getLocality();
                                textView2.setText(userAddress + ", " + userCountry);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.d("TAG", "onSuccess: " + e.toString());
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

