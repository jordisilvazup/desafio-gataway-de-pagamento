package br.com.zup.edu.desafiopagamentos.exception;

import java.net.BindException;

public class PagamentoNaoProcessadoException extends BindException {
    public PagamentoNaoProcessadoException(String msg) {
        super(msg);
    }
}
