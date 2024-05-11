package com.alessandrasantana.app_zoologico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public Button botao;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewDados = (ListView)  findViewById(R.id.listViewDados);
        botao = (Button) findViewById(R.id.buttonCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        listViewDados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                confirmaExcluir();
                return true;
            }
        });

        listViewDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaAlterar();
            }
        });

        criarBancoDados();
        listarDados();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarDados();
    }

    public void criarBancoDados() {
            try {
                bancoDados = openOrCreateDatabase("crud_zoo", MODE_PRIVATE, null);
                bancoDados.execSQL("CREATE TABLE IF NOT EXISTS animais(" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                        " , nome VARCHAR)");
                bancoDados.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void listarDados() {
            try {
                arrayIds = new ArrayList<>();
                bancoDados = openOrCreateDatabase("crud_zoo", MODE_PRIVATE, null);
                Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome FROM animais", null);
                ArrayList<String> linhas = new ArrayList<String>();
                ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        linhas
                );
                listViewDados.setAdapter(meuAdapter);
                meuCursor.moveToFirst();
                while(meuCursor!=null) {
                    linhas.add(meuCursor.getString(1));
                    arrayIds.add(meuCursor.getInt(0));
                    meuCursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void abrirTelaCadastro() {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        }

    public void confirmaExcluir() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(MainActivity.this);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Você realmente deseja excluir esse animal?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                excluir();
                listarDados();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        msgBox.show();
    }

    public void excluir() {
        try {
            bancoDados = openOrCreateDatabase("crud_zoo", MODE_PRIVATE, null);
            String sql = "DELETE FROM animais WHERE id =?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, idSelecionado);
            stmt.executeUpdateDelete();
            listarDados();
            bancoDados.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void abrirTelaAlterar(){
        Intent intent = new Intent(this, AlterarActivity.class);
        intent.putExtra("id",idSelecionado);
        startActivity(intent);
    }
}