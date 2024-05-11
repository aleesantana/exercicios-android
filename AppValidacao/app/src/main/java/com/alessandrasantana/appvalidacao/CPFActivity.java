package com.alessandrasantana.appvalidacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CPFActivity extends AppCompatActivity {

    EditText editTextCPF;
    Button buttonValidarCPF;
    Button btnReceitaFederal;
    Button btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpfactivity);

        buttonValidarCPF = findViewById(R.id.buttonValidarCPF);
        editTextCPF = findViewById(R.id.editTextCPF);
        btnReceitaFederal = findViewById(R.id.buttonReceita);
        btnVoltar = findViewById(R.id.btnVoltar);

        buttonValidarCPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editTextCPF.getText().toString().trim();

                if (isValidCPF(cpf)) {
                    Toast.makeText(CPFActivity.this, "CPF válido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CPFActivity.this, "CPF inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReceitaFederal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://servicos.receita.fazenda.gov.br/Servicos/CPF/ConsultaSituacao/ConsultaPublica.asp";
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
    }

    private boolean isValidCPF(String cpf) {
        if (TextUtils.isEmpty(cpf)) {
            return false;
        }

        // Remove caracteres que não são dígitos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais, o que é inválido para um CPF
        boolean allEqual = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            return false;
        }

        // Algoritmo de validação de CPF
        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
        }
        int sum = 0;
        int factor = 10;
        for (int i = 0; i < 9; i++) {
            sum += cpfArray[i] * factor;
            factor--;
        }
        int remainder = sum % 11;
        int digit1 = remainder < 2 ? 0 : 11 - remainder;

        sum = 0;
        factor = 11;
        for (int i = 0; i < 10; i++) {
            sum += cpfArray[i] * factor;
            factor--;
        }
        remainder = sum % 11;
        int digit2 = remainder < 2 ? 0 : 11 - remainder;

        return (cpfArray[9] == digit1 && cpfArray[10] == digit2);
    }
}