package com.restaurante.model;

import com.restaurante.controller.ReservaController;
import com.restaurante.model.exception.MesaIndisponivelException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private String nome;
    private List<Mesa> mesas;
    private List<Reserva> reservas;
    private ReservaController reservaController;

    public Restaurante(String nome) {
        this.nome = nome;
        this.mesas = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.reservaController = new ReservaController(this);
    }


    public List<Mesa> getMesas() {
        return mesas;
    }

    public void adicionarMesa(Mesa mesa) {
        mesas.add(mesa);
    }

    public void removerMesa(int numeroMesa) {
        mesas.removeIf(mesa -> mesa.getNumero() == numeroMesa);
    }

    public List<Reserva> listarReservas() {
        return reservas;
    }

    public Reserva fazerReserva(Cliente cliente, int numeroMesa, String data, String horaChegada) throws MesaIndisponivelException {
        Mesa mesaEncontrada = buscarMesa(numeroMesa);
        if (mesaEncontrada == null) {
            throw new MesaIndisponivelException("A mesa " + numeroMesa + " não existe.");
        }

        if (mesaEncontrada.isDisponivel()) {
            Reserva reserva = new Reserva(cliente, mesaEncontrada, data, horaChegada);
            reservas.add(reserva);
            reserva.confirmarReserva();
            reservaController.salvarReservas();
            return reserva;
        } else {
            throw new MesaIndisponivelException("A mesa " + numeroMesa + " já está reservada.");
        }
    }

    public void cancelarReserva(Reserva reserva) {
        reserva.cancelarReserva();
        reservas.remove(reserva);
        reservaController.salvarReservas();
    }

    public Mesa buscarMesa(int numero) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == numero) {
                return mesa;
            }
        }
        return null;
    }

    public void carregarReservas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("reservas.csv"))) {
            String linha;
            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 6 || partes.length == 7) {
                    try {
                        int id = Integer.parseInt(partes[0]);
                        String nomeCliente = partes[1];
                        String telefoneCliente = partes[2];
                        int numeroMesa = Integer.parseInt(partes[3]);
                        String data = partes[4];
                        String horaInicio = partes[5];
                        String horaFim = partes.length == 7 ? (partes[6].equals("null") ? null : partes[6]) : null;

                        Mesa mesa = buscarMesa(numeroMesa);
                        if (mesa != null) {
                            Cliente cliente = new Cliente(nomeCliente, telefoneCliente);
                            Reserva reserva = new Reserva(cliente, mesa, data, horaInicio, horaFim);
                            reservas.add(reserva);
                            if (horaFim == null) {
                                mesa.ocuparMesa();
                            }
                        } else {
                            System.err.println("Mesa não encontrada: " + numeroMesa);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Número inválido encontrado: " + e.getMessage());
                    }
                } else {
                    System.err.println("Linha inválida encontrada e ignorada: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar reservas: " + e.getMessage());
        }
    }





}
