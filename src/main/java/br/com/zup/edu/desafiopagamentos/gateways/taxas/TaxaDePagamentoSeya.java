package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamento;

import java.math.BigDecimal;

public class TaxaDePagamentoSeya implements TaxaDePagamento {

    @Override
    public BigDecimal taxa(BigDecimal valorPagamento) {
        return new BigDecimal("6.00");
    }
}
