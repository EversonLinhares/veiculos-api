package br.com.veiculos.domain.exception;

public class NegocioException extends RuntimeException{

    public NegocioException(String msg) {
        super(msg);
    }
}
