package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    Button btnLogin;

    // HashMap para armazenar usuários e senhas
    HashMap<String, String> usuarios;

    // HashMap para armazenar dicas de senha
    HashMap<String, String> dicasSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa os HashMaps de usuários e dicas de senha
        inicializarUsuarios();

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);

        edtEmail.requestFocus();

        //configuração do botão
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                // Verifica se o usuário existe
                if (usuarios.containsKey(usuario)) {
                    // Verifica se a senha está correta
                    if (usuarios.get(usuario).equals(senha)) {
                        // Login bem-sucedido
                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                    } else {
                        // Senha incorreta
                        sugerirDicaSenha(usuario);
                    }
                } else {
                    // Usuário inexistente
                    mensagem("Usuário inexistente");
                }
            }
        });
    }

    // Inicializa os usuários e dicas de senha
    private void inicializarUsuarios() {
        usuarios = new HashMap<>();
        dicasSenha = new HashMap<>();

        // Adiciona usuários e senhas
        usuarios.put("admin", "admin");
        dicasSenha.put("admin", "Dica: A senha é igual ao nome do usuário.");

        usuarios.put("juca@gmail.com", "1234");
        dicasSenha.put("juca@gmail.com", "Dica: A senha contém somente números.");

        usuarios.put("ana@gmail.com", "Ana123");
        dicasSenha.put("ana@gmail.com", "Dica: A senha contém letras e números.");
    }

    private void mensagem(String info) {
        Toast.makeText(MainActivity.this, info, Toast.LENGTH_LONG).show();
        edtEmail.requestFocus();
    }

    // Exibe a dica de senha para o usuário
    private void sugerirDicaSenha(final String usuario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Senha incorreta! Gostaria de ver a dica de senha?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Se o usuário clicar em "Sim", exibe a dica de senha
                        Toast.makeText(MainActivity.this, dicasSenha.get(usuario), Toast.LENGTH_LONG).show();
                        edtSenha.setText(""); // Limpa o campo de senha
                        edtSenha.requestFocus(); // Foca no campo de senha
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Se o usuário clicar em "Não", fecha o diálogo e mantém na tela de login
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
