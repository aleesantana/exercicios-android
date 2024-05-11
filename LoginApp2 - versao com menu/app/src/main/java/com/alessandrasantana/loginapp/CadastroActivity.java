package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public class CadastroActivity extends AppCompatActivity {

    EditText editTextNome;

    EditText editTextQuantidade;
    Button botao;
    SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextQuantidade = (EditText) findViewById(R.id.editTextQuantidade);

        botao = (Button) findViewById(R.id.btnSalvar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    public void cadastrar() {
        Log.d("CadastroActivity", "Método cadastrar() foi chamado.");

        String nome = editTextNome.getText().toString();
        String quantidadeStr = editTextQuantidade.getText().toString();

        if(!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(quantidadeStr)){
            try {
                int quantidade = Integer.parseInt(quantidadeStr);

                bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
                String sql = "INSERT INTO item (nome, quantidade) VALUES (?, ?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, nome);
                stmt.bindLong(2, quantidade);
                stmt.executeInsert();

                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}