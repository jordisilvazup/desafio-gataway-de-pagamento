package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamento;

import java.math.BigDecimal;

public class TaxaDePagamentoSaori implements TaxaDePagamento {
    @Override
    public BigDecimal taxa(BigDecimal valorPagamento) {
        BigDecimal valorTaxaFixa = new BigDecimal("0.05");
        return valorPagamento.multiply(valorTaxaFixa);
    }
}
