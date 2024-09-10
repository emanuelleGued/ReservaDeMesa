package com.restaurante.model;




import static com.restaurante.controller.ReservaController.getNextId;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Mesa mesa;
    private String data;
    private String hora;
    private String horaSaida;
    private boolean finalizada;

    // Construtor com ID
    public Reserva(Cliente cliente, Mesa mesa, String data, String horaInicio, String horaSaida) {
        this.id = getNextId();
        this.cliente = cliente;
        this.mesa = mesa;
        this.data = data;
        this.hora = horaInicio;
        this.horaSaida = horaSaida;
        this.finalizada = horaSaida != null;
    }

    // Construtor sem ID (usado para novas reservas)
    public Reserva(Cliente cliente, Mesa mesa, String data, String horaInicio) {
        this(cliente, mesa, data, horaInicio, null);
    }


    public Cliente getCliente() {
        return cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public boolean isFinalizada() {
        return finalizada;

    }

    public void confirmarReserva() {
        mesa.ocuparMesa();
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
        this.finalizada = horaSaida != null;
    }

    public void cancelarReserva() {
        mesa.liberarMesa();
    }

    public long calcularDuracaoEmMinutos() {
        // Verificar se a hora de saída é nula ou a string é "null"
        if (horaSaida == null || horaSaida.equals("null") || horaSaida.isEmpty()) {
            return 0; // Retorna 0 minutos se a hora de saída for nula ou inválida
        }

        if (hora == null || hora.isEmpty()) {
            throw new IllegalArgumentException("Hora de início não pode ser nula.");
        }

        String[] inicio = hora.split(":");
        String[] fim = horaSaida.split(":");

        try {
            int inicioMinutos = Integer.parseInt(inicio[0]) * 60 + Integer.parseInt(inicio[1]);
            int fimMinutos = Integer.parseInt(fim[0]) * 60 + Integer.parseInt(fim[1]);

            // Ajustar caso o horário de saída seja no dia seguinte
            if (fimMinutos < inicioMinutos) {
                fimMinutos += 24 * 60;
            }

            return fimMinutos - inicioMinutos;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de hora inválido.", e);
        }
    }




    public void liberarMesa(){
        mesa.liberarMesa();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
