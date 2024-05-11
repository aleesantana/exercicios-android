package com.alessandrasantana.gps_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Ponto p1, p2;
    private String PROVIDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PROVIDER = LocationManager.GPS_PROVIDER;
        zerarPontos();
    }

    private void zerarPontos(){
        p1 = new Ponto();
        p2 = new Ponto();
    }

    public void reset(View v) {
        zerarPontos();
        ((EditText)findViewById(R.id.edtPonto1)).setText("");
        ((EditText)findViewById(R.id.edtPonto2)).setText("");
    }

    public void lerPonto1(View v) {
        p1 = this.getPonto();
        ((EditText)findViewById(R.id.edtPonto1)).setText(p1.toString());
    }

    public void lerPonto2(View v) {
        p2 = this.getPonto();
        ((EditText)findViewById(R.id.edtPonto2)).setText(p2.toString());
    }

    public Ponto getPonto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return null;
        }

        LocationManager mLocManager  = (LocationManager) getSystemService(MainActivity.this.LOCATION_SERVICE);
        LocationListener mLocListener = new MinhaLocalizacaoListener();

        mLocManager.requestLocationUpdates(PROVIDER, 0, 0, mLocListener);

        //Location localAtual = mLocManager.getLastKnownLocation(PROVIDER);

        if (! mLocManager.isProviderEnabled(PROVIDER)) {
            Toast.makeText(MainActivity.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
        }

        //return new Ponto(localAtual.getLatitude, localAtual.getLongitude, localAtual.getAltitude);
        return new Ponto(MinhaLocalizacaoListener.latitude,
                MinhaLocalizacaoListener.longitude,
                MinhaLocalizacaoListener.altitude);
    }

    public void verPonto1(View v) { mostrarGoogleMaps(p1.getLatitude(), p1.getLongitude());}

    public void verPonto2(View v) { mostrarGoogleMaps(p2.getLatitude(), p2.getLongitude());}

    @SuppressLint("SetJavaScriptEnabled")
    public void mostrarGoogleMaps(double latitude, double longitude) {
        WebView wv = findViewById(R.id.webv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude);
    }

    public void calcularDistancia(View v) {
        //LocationManager mLocManager  = (LocationManager) getSystemService(MainActivity.this.LOCATION_SERVICE);
        float[] resultado = new float[1];
        Location.distanceBetween(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude(), resultado);
        Toast.makeText(MainActivity.this, "DISTÂNCIA: " + resultado[0] + "m", Toast.LENGTH_LONG).show();
    }

}