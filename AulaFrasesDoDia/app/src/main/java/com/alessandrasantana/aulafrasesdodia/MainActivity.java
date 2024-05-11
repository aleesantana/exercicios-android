package com.alessandrasantana.aulafrasesdodia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Atributos
    String[] frases = {
            "Frase 01",
            "Frase 02",
            "Frase 03",
    };

    /**
     * De acordo com o que quero criar, o nome pode ser: textResultado, buttonResultado, editResultado
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gerarFrase(View view){
        TextView texto = findViewById(R.id.textResultado);
        int numeroAleatorio = new Random().nextInt(3);
        String frase = frases[numeroAleatorio];
        texto.setText(frase);
    }

    public void exibirTodas(View view){

        //conectando o TextView no método,encontrando pelo id
        TextView texto = findViewById(R.id.textResultado);

        String textoResultado = "";

        //usando o for com : ele percorre cada um dos itens do array frases e armazenar na String frase.
        // Isso acontece todas as vezes que ele percorrer o array
        //a string textoResultado é vazia, e irá juntar com cada frase do array em cada loop for
        for(String frase : frases){
            textoResultado = textoResultado + frase + "\n";
        }
        texto.setText(textoResultado);
    }
}