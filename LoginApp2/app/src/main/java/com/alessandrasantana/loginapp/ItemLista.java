package com.alessandrasantana.loginapp;

public class ItemLista {
    private String nome;
    private int quantidade;

    public ItemLista(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

}
