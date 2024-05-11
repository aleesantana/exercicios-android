package com.alessandrasantana.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Identificar qual item de menu foi selecionado
        int id = item.getItemId();

        if (id == R.id.itemListar) {
            Intent intent = new Intent(MenuActivity.this, ListagemActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.itemCadastrar) {
            Intent intent = new Intent(MenuActivity.this, CadastroActivity.class);
            startActivity(intent);
            return true;
        }
            return super.onOptionsItemSelected(item);
        }
    }