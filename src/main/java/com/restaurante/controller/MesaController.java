package com.restaurante.controller;

import com.restaurante.model.Mesa;
import com.restaurante.model.Restaurante;

import java.util.List;

public class MesaController {
    private Restaurante restaurante;

    public MesaController(Restaurante restaurante) {
        if (restaurante == null) {
            throw new IllegalArgumentException("Restaurante não pode ser nulo.");
        }
        this.restaurante = restaurante;
    }



    public List<Mesa> listarMesas() {
        List<Mesa> mesas = restaurante.getMesas();
        if (mesas == null) {
            throw new IllegalStateException("A lista de mesas no restaurante não foi inicializada.");
        }
        return mesas;
    }


}
