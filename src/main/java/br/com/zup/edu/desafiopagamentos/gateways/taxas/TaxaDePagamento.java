package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import java.math.BigDecimal;

public interface TaxaDePagamento {
    BigDecimal taxa(BigDecimal valorPagamento);
}
