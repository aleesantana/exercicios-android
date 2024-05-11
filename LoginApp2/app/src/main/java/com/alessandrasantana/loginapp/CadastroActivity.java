package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        botao = (Button) findViewById(R.id.btnSalvar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    public void cadastrar() {
        String nome = editTextNome.getText().toString();
        String quantidadeText = editTextQuantidade.getText().toString(); // Obtém a quantidade do campo de quantidade
        int quantidade = 0; // Inicializa a quantidade como zero

        // Verifica se o campo de nome não está vazio e se a quantidade é um número válido
        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(quantidadeText)) {
            quantidade = Integer.parseInt(quantidadeText); // Converte o texto da quantidade para um inteiro
        }

        try {
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
            String sql = "INSERT INTO item (nome, quantidade) VALUES (?, ?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1, nome);
            stmt.bindLong(2, quantidade); // Adiciona a quantidade ao statement
            stmt.executeInsert();

            bancoDados.close();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
