package com.restaurante.model;

public class Mesa {
    private int numero;
    private int capacidade;
    private boolean disponivel;

    public Mesa(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.disponivel = true;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    void ocuparMesa() {
        this.disponivel = false;
    }

    public void liberarMesa() {
        this.disponivel = true;
    }
}
