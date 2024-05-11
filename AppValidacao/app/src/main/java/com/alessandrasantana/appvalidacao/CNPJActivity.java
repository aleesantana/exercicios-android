package com.alessandrasantana.appvalidacao;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CNPJActivity extends AppCompatActivity {

    Button btnReceita;
    Button btnVoltar;
    Button buttonValidarCNPJ;
    EditText editTextCNPJ;
    TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnpjactivity);

        btnReceita = findViewById(R.id.btnReceita);
        btnVoltar = findViewById(R.id.btnVoltar);
        buttonValidarCNPJ = findViewById(R.id.buttonValidarCNPJ);
        editTextCNPJ = findViewById(R.id.editTextCNPJ);
        textViewResultado = findViewById(R.id.textViewResultado);

        btnReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.receitaws.com.br/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonValidarCNPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cnpj = editTextCNPJ.getText().toString().trim();
                if (isValidCNPJ(cnpj)) {
                    // CNPJ válido
                    Toast.makeText(getApplicationContext(), "CNPJ válido", Toast.LENGTH_SHORT).show();
                } else {
                    // CNPJ inválido
                    Toast.makeText(getApplicationContext(), "CNPJ inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para validar o CNPJ
    private boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14)
            return false;

        try {
            long cnpjNumeros = Long.parseLong(cnpj);
            if (cnpjNumeros == 0)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }

        int soma = 0;
        int peso = 2;
        String digito;
        int digitoVerificador1, digitoVerificador2;

        for (int i = 11; i >= 0; i--) {
            soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso;
            peso++;
            if (peso == 10)
                peso = 2;
        }

        digito = String.valueOf(11 - soma % 11);
        digitoVerificador1 = (digito.equals("10") || digito.equals("11")) ? 0 : Integer.parseInt(digito);

        soma = 0;
        peso = 2;

        for (int i = 12; i >= 0; i--) {
            soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso;
            peso++;
            if (peso == 10)
                peso = 2;
        }

        digito = String.valueOf(11 - soma % 11);
        digitoVerificador2 = (digito.equals("10") || digito.equals("11")) ? 0 : Integer.parseInt(digito);

        return digitoVerificador1 == Integer.parseInt(cnpj.substring(12, 13)) && digitoVerificador2 == Integer.parseInt(cnpj.substring(13));

    }
}
