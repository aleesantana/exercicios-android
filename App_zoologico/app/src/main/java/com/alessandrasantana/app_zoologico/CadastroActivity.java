package com.alessandrasantana.app_zoologico;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {
    SQLiteDatabase bancoDados;
    EditText editTextAnimal;
    Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextAnimal = (EditText) findViewById(R.id.editTextAnimal);
        botao = (Button) findViewById(R.id.buttonCadastrar2);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    public void cadastrar() {
        if (!TextUtils.isEmpty(editTextAnimal.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("crud_zoo", MODE_PRIVATE, null);
                String sql = "INSERT INTO animais (nome) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTextAnimal.getText().toString());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}