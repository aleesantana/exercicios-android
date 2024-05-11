package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public ListView listViewDados;
    public Button botao;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        listViewDados = (ListView) findViewById(R.id.listViewDados);
        botao = (Button) findViewById(R.id.btnCadastrar);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });

        // Configuração do longclick para exclusão de item
        listViewDados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                confirmaExcluir();
                return true;
            }
        });

        // click para chamar a tela de edição
        listViewDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaAlterar();
            }
        });

        criarBancoDados();
        //inserirDadosTemp();
        listarDados();
    }

    //Aqui será atualizada a tela de listagem após inserir um novo item
    @Override
    protected void onResume() {
        super.onResume();
        listarDados();
    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS item(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados() {
        try {
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome, quantidade FROM item", null);
            ArrayList<ItemLista> listaItens = new ArrayList<>(); // Lista de itens com nome e quantidade
            while (meuCursor.moveToNext()) {
                int id = meuCursor.getInt(0);
                String nome = meuCursor.getString(1);
                int quantidade = meuCursor.getInt(2);
                arrayIds.add(id);

                // Cria um objeto ItemLista com o nome e a quantidade e adiciona à lista
                ItemLista item = new ItemLista(nome, quantidade);
                listaItens.add(item);
            }
            meuCursor.close();

            // Cria um adaptador personalizado com a lista de itens e define na ListView
            ListaAdapter adapter = new ListaAdapter(this, listaItens);
            listViewDados.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirTelaCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void abrirTelaAlterar() {
        Intent intent = new Intent(this, AlterarActivity.class);
        intent.putExtra("id", idSelecionado);
        startActivity(intent);
    }

    public void confirmaExcluir() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(ListagemActivity.this);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Você realmente deseja excluir esse registro?");
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
    public void voltar(View view) {
        finish();
    }

    public void excluir() {
        //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show();
        try {
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
            String sql = "DELETE FROM item WHERE id =?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindLong(1, idSelecionado);
            stmt.executeUpdateDelete();
            listarDados();
            bancoDados.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
}