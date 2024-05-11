package com.alessandrasantana.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CadastroActivity extends AppCompatActivity {

    EditText editTextNome;
    Button botao;
    SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        botao = (Button) findViewById(R.id.btnSalvar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Cadastro", "Cadastro", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        botao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                cadastrar();

                //adiciona notificação
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CadastroActivity.this, "Cadastro");
                builder.setContentTitle("Cadastro");
                builder.setContentText("Cadastro realizado com sucesso.");
                builder.setSmallIcon(R.drawable.ic_notificacao);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CadastroActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });
    }

    public void cadastrar() {
        if (!TextUtils.isEmpty(editTextNome.getText().toString())) {
            try {
                bancoDados = openOrCreateDatabase("aplicacaodb", MODE_PRIVATE, null);
                String sql = "INSERT INTO item (nome) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editTextNome.getText().toString());
                stmt.executeInsert();

                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}