package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

import java.math.BigDecimal;

public class ProcessaPagamento {
    private BigDecimal valorPedido;
    private PagamentoRequest pagamentoRequest;
    private FormaDePagamento formaDePagamento;

    public ProcessaPagamento(BigDecimal valorPedido, PagamentoRequest pagamentoRequest, FormaDePagamento formaDePagamento) {
        this.valorPedido = valorPedido;
        this.pagamentoRequest = pagamentoRequest;
        this.formaDePagamento = formaDePagamento;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public PagamentoRequest getPagamentoRequest() {
        return pagamentoRequest;
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }
}
