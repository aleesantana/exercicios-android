package com.alessandrasantana.appvalidacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonCPF;
    Button buttonCNPJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCPF = findViewById(R.id.buttonCPF);
        buttonCNPJ = findViewById(R.id.buttonCNPJ);

        buttonCPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CPFActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonCNPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CNPJActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}