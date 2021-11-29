package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamento;

import java.math.BigDecimal;

public class TaxaDePagamentoTango implements TaxaDePagamento {
    @Override
    public BigDecimal taxa(BigDecimal valorPagamento) {

        if (valorPagamento.compareTo(new BigDecimal("100"))<=0){

            BigDecimal taxaFixa = new BigDecimal("4.00");

            return taxaFixa;
        }

        BigDecimal taxaVariavel = new BigDecimal("0.06");

        return valorPagamento.multiply(taxaVariavel);
    }
}
