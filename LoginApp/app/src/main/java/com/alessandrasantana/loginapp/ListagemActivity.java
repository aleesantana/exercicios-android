package com.alessandrasantana.loginapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    public ListView listViewDados;

    public Button botaoCadastro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        listViewDados = (ListView) findViewById(R.id.listViewDados);
        botaoCadastro = (Button) findViewById(R.id.buttonCadastar);

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        // Abrir o banco de dados uma vez aqui
        bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);

        criarBancoDados();
        inserirDadosTemp();
        listarDados();

        // Fechar o banco de dados aqui após todos os usos
        bancoDados.close();
    }

    public void criarBancoDados(){
        try {
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS item(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR )");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados(){
        try {
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM item", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);

            if (meuCursor != null && meuCursor.moveToFirst()) {
                do {
                    linhas.add(meuCursor.getString(1));
                } while (meuCursor.moveToNext());
            }
            meuCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inserirDadosTemp(){
        try {
            String sql = "INSERT INTO item (nome) VALUES (?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            stmt.bindString(1, "Item 1");
            stmt.executeInsert();

            stmt.bindString(1, "Item 2");
            stmt.executeInsert();

            stmt.bindString(1, "Item 3");
            stmt.executeInsert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
}
