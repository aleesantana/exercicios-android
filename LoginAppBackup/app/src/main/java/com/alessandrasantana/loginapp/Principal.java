package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Principal extends AppCompatActivity {
    private EditText etNome;
    private EditText etEndereco;
    private RadioGroup rgGenero = null;
    private TextView textCalendar;
    private Button enviarData;
    private ArrayList<String> pessoasFisicas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Data de nascimento
        textCalendar = findViewById(R.id.textCalendar);
        enviarData = findViewById(R.id.enviarData);

        enviarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        // Referenciar os EditTexts
        etNome = findViewById(R.id.et_nome);
        etEndereco = findViewById(R.id.et_endereco);

        // RadioGroup Fem e Masc
        rgGenero = (RadioGroup) findViewById(R.id.rg_genero);

        Button btBotao = (Button) findViewById(R.id.bt_salvar);
        btBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Coletar os dados digitados
                String nome = etNome.getText().toString();
                String endereco = etEndereco.getText().toString();
                RadioButton rbGenero = findViewById(rgGenero.getCheckedRadioButtonId());
                String genero = rbGenero.getText().toString();
                String dataNascimento = textCalendar.getText().toString();

                // Adicionar os dados ao array de pessoasFisicas
                pessoasFisicas.add("Nome: " + nome + "\nEndereço: " + endereco + "\nGênero: " + genero + "\nData de Nascimento: " + dataNascimento);

                // Exibir mensagem de sucesso
                Toast.makeText(Principal.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void voltar(View view) {
        finish();
    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                textCalendar.setText("Data de nascimento: " + String.valueOf(year) + "." + String.valueOf(month+1) + "." + String.valueOf(day));
            }
        }, 2024, 3, 05);
        dialog.show();
    }
}