package com.restaurante.model;

public class Cliente {
    private String nome;
    private String telefone;

    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
        validarNumero(telefone);
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }


    private void validarNumero(String telefone) {
        if (telefone.length() < 9) {
            throw new IllegalArgumentException("Número de telefone inválido, deve ter no mínimo 9 caracteres.");
        }
    }
}
