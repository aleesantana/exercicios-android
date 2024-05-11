package com.alessandrasantana.loginapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ListaAdapter extends ArrayAdapter<ItemLista> {

    private LayoutInflater inflater;
    private ArrayList<ItemLista> itens;

    public ListaAdapter(Context context, ArrayList<ItemLista> itens) {
        super(context, 0, itens);
        this.itens = itens;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.nomeTextView = convertView.findViewById(R.id.textViewNomeItem);
            holder.quantidadeTextView = convertView.findViewById(R.id.textViewQuantidadeItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtém o item da lista na posição atual
        ItemLista item = getItem(position);

        // Define o nome e a quantidade nos elementos de texto
        holder.nomeTextView.setText(item.getNome());
        holder.quantidadeTextView.setText(String.valueOf(item.getQuantidade()));

        return convertView;
    }

    private static class ViewHolder {
        TextView nomeTextView;
        TextView quantidadeTextView;
    }
}

