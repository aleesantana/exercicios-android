package com.alessandrasantana.firstjavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etNome = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.it_nome);
        Button btEnviar = findViewById(R.id.bt_enviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNome.getText().toString().isEmpty() )
                {
                    Toast.makeText(MainActivity.this, "Por favor, digite seu nome " + etNome.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Obrigado, " + etNome.getText().toString() + " por estudar Android! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}