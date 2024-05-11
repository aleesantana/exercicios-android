package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class ListagemActivity extends AppCompatActivity {

    private static final int DATABASE_VERSION = 2;
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

        // Verifica e execute a atualização do esquema do banco de dados, se necessário
        int currentVersion = getCurrentDatabaseVersion();
        if (currentVersion < DATABASE_VERSION) {
            updateDatabaseSchema(currentVersion, DATABASE_VERSION);
        }

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

    private int getCurrentDatabaseVersion() {
        SharedPreferences preferences = getSharedPreferences("DATABASE", MODE_PRIVATE);
        return preferences.getInt("DB_VERSION", 0);
    }

    private void updateDatabaseSchema(int currentVersion, int newVersion) {
        try {
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);

            // Verifica se a coluna quantidade já existe na tabela
            Cursor cursor = bancoDados.rawQuery("PRAGMA table_info(item)", null);
            if (cursor != null) {
                boolean columnExists = false;
                int nameIndex = cursor.getColumnIndex("name");
                if (nameIndex != -1) {
                    while (cursor.moveToNext()) {
                        String columnName = cursor.getString(nameIndex);
                        if (columnName.equals("quantidade")) {
                            columnExists = true;
                            break;
                        }
                    }
                }
                cursor.close();

                // Se a coluna não existe, adicione-a
                if (!columnExists) {
                    bancoDados.execSQL("ALTER TABLE item ADD COLUMN quantidade INTEGER");
                }

                // Atualize a versão do banco de dados após a atualização bem-sucedida
                updateDatabaseVersion(newVersion);
            }

            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDatabaseVersion(int newVersion) {
        SharedPreferences preferences = getSharedPreferences("DATABASE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("DB_VERSION", newVersion);
        editor.apply();
    }


    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS item(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, quantidade INTEGER)");
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarDados() {
        Log.d("ListagemActivity", "Método listarDados() foi chamado.");
        try {
            arrayIds = new ArrayList<>();
            bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, nome, quantidade FROM item", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    linhas
            );
            listViewDados.setAdapter(meuAdapter);
            while (meuCursor.moveToNext()) {
                linhas.add(meuCursor.getString(1)); // Retorna o nome, que está na posição 1
                arrayIds.add(meuCursor.getInt(0)); // Retorna o id, que está na posição zero
            }
            meuCursor.close(); // Feche o cursor quando terminar de usá-lo
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