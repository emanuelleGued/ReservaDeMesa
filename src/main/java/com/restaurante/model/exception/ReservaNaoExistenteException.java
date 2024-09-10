package com.restaurante.model.exception;

public class ReservaNaoExistenteException extends Exception {
    public ReservaNaoExistenteException(String mensagem) {
        super(mensagem);
    }
}
