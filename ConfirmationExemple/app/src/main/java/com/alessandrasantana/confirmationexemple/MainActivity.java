package com.alessandrasantana.confirmationexemple;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etNome = null;
    private Button btConfirmacaoButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.it_nome);
        Button btEnviar = findViewById(R.id.bt_enviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etNome.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, digite seu nome " + etNome.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Obrigado, " + etNome.getText().toString() + " por estudar Android! ", Toast.LENGTH_LONG).show();
                }
            }
        });
        btConfirmacaoButton = (Button) findViewById(R.id.bt_confirmacao);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        btConfirmacaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Você está aprendendo Android?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); //fecha a caixa de diálogo atual
                        Toast.makeText(getApplicationContext(), "Parabéns, continue estudando!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Favor estudar e concentrar-se mais!", Toast.LENGTH_SHORT).show();
                    }
                });

                //Cria a caixa de diálogo
                AlertDialog alert = builder.create();
                alert.setTitle("Responda a pergunta abaixo");
                alert.show();
            }
        });
    }
}