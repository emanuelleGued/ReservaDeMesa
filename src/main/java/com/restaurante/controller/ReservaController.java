package com.restaurante.controller;

import com.restaurante.model.Cliente;
import com.restaurante.model.Mesa;
import com.restaurante.model.Reserva;
import com.restaurante.model.Restaurante;
import com.restaurante.model.exception.HoraInvalidaException;
import com.restaurante.model.exception.MesaIndisponivelException;
import com.restaurante.model.exception.ReservaNaoExistenteException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservaController {
    private Restaurante restaurante;
    private List<Reserva> reservas;

    public ReservaController(Restaurante restaurante) {
        this.restaurante = restaurante;
        this.reservas = restaurante.listarReservas();
    }

    public void fazerReservaMesa(Cliente cliente, int numeroMesa, String data, String horaChegada) throws MesaIndisponivelException {
        restaurante.fazerReserva(cliente, numeroMesa, data, horaChegada);
        this.reservas = restaurante.listarReservas();
    }


    public void cancelarReserva(Reserva reserva) {
        if (reserva != null) {
            restaurante.cancelarReserva(reserva);
            this.reservas = restaurante.listarReservas();
        } else {
            throw new IllegalArgumentException("Reserva não encontrada!");
        }
    }

    public Reserva buscarReservaPorNumeroId(String reservaId) {
        for (Reserva reserva : reservas) {
            if (String.valueOf(reserva.getId()).equals(reservaId)) {
                return reserva;
            }
        }
        return null;
    }

    public List<Reserva> listarReservas() {
        return restaurante.listarReservas();
    }

    public void gerarEstatisticas(String caminhoArquivo) {
        List<Reserva> reservas = restaurante.listarReservas();

        int totalReservas = reservas.size();
        int totalMesas = restaurante.getMesas().size();
        int totalMesasOcupadas = (int) reservas.stream()
                .map(Reserva::getMesa)
                .distinct()
                .count();

        // Mapas para armazenar o tempo total e a quantidade de reservas por mesa
        Map<Mesa, Long> tempoTotalPorMesa = new HashMap<>();
        Map<Mesa, Integer> contagemReservasPorMesa = new HashMap<>();

        // Itera sobre todas as reservas para calcular o tempo total e a contagem por mesa
        for (Reserva reserva : reservas) {
            if (reserva.getHora() != null && !reserva.getHora().isEmpty() &&
                    reserva.getHoraSaida() != null && !reserva.getHoraSaida().isEmpty()) {

                // Calcula a duração da reserva em minutos
                long duracao = reserva.calcularDuracaoEmMinutos();
                Mesa mesa = reserva.getMesa();

                // Acumula o tempo total de uso para cada mesa
                tempoTotalPorMesa.put(mesa, tempoTotalPorMesa.getOrDefault(mesa, 0L) + duracao);

                // Conta o número de reservas para cada mesa
                contagemReservasPorMesa.put(mesa, contagemReservasPorMesa.getOrDefault(mesa, 0) + 1);
            }
        }

        // Escreve as estatísticas no arquivo especificado
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("Estatísticas de Reservas:\n");
            writer.write("Total de Reservas: " + totalReservas + "\n");
            writer.write("Total de Mesas: " + totalMesas + "\n");
            writer.write("Mesas Ocupadas: " + totalMesasOcupadas + "\n");

            if (tempoTotalPorMesa.isEmpty()) {
                writer.write("Nenhuma reserva com dados válidos foi encontrada.\n");
            } else {
                writer.write("Tempo Total de Uso das Mesas (em minutos):\n");

                for (Map.Entry<Mesa, Long> entry : tempoTotalPorMesa.entrySet()) {
                    Mesa mesa = entry.getKey();
                    long tempoTotal = entry.getValue();
                    int contagemReservas = contagemReservasPorMesa.getOrDefault(mesa, 1);

                    // Calcula o tempo médio de uso da mesa por reserva
                    double tempoMedio = (double) tempoTotal / contagemReservas;

                    writer.write("Mesa " + mesa.getNumero() + ":\n");
                    writer.write("  Tempo Total: " + tempoTotal + " minutos\n");
                    writer.write("  Tempo Médio por Reserva: " + String.format("%.2f", tempoMedio) + " minutos\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao escrever o arquivo: " + e.getMessage());
        }
    }


    // Metodo para fializar reserva
    public boolean finalizarReservaMesa(int idReserva, String horaFim) throws ReservaNaoExistenteException, HoraInvalidaException {
        // Busca a reserva pelo ID
        Reserva reserva = buscarReservaPorNumeroId(String.valueOf(idReserva));
        if (reserva == null) {
            throw new ReservaNaoExistenteException("Reserva não encontrada para o ID " + idReserva);
        }

        // Verifica se a reserva já foi finalizada
        if (reserva.getHoraSaida() != null) {
            throw new IllegalStateException("A reserva já foi finalizada.");
        }

        // Valida a hora de saída
        if (!validarHoraFim(reserva, horaFim)) {
            throw new HoraInvalidaException("Hora de saída inválida! Deve estar no formato HH:mm e ser posterior à hora de chegada.");
        }

        // Define a hora de saída
        reserva.setHoraSaida(horaFim);

        // Marca a mesa como disponível se a hora de saída não for null
        reserva.getMesa().liberarMesa();

        // Salva as reservas atualizadas
        salvarReservas();

        return true;
    }




    // Metodo para salvar os dados das reservas em reservas.csv
    public void salvarReservas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("reservas.csv"))) {
            // Escreve o cabeçalho no arquivo
            writer.println("ID,Nome,Telefone,Número da Mesa,Data,Hora de Início,Hora de Fim");

            // Escreve os dados das reservas
            for (Reserva reserva : reservas) {
                writer.println(reserva.getId() + "," +
                        reserva.getCliente().getNome() + "," +
                        reserva.getCliente().getTelefone() + "," +
                        reserva.getMesa().getNumero() + "," +
                        reserva.getData() + "," +
                        reserva.getHora() + "," +
                        reserva.getHoraSaida());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar reservas: " + e.getMessage());
        }
    }



    // Metodo para validação de horario de saida
    private boolean validarHoraFim(Reserva reserva, String horaFim) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date horaChegada = sdf.parse(reserva.getHora());
            Date horaSaida = sdf.parse(horaFim);
            return horaSaida.after(horaChegada);
        } catch (ParseException e) {
            return false;
        }
    }

    private static int proximoId = 1; // Valor inicial, deve ser atualizado no carregamento

    public static int getNextId() {
        return proximoId++;
    }
}
