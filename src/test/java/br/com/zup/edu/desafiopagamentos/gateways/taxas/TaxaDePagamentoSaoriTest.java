package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxaDePagamentoSaoriTest {
    private TaxaDePagamentoSaori taxaDePagamento;

    @BeforeEach
    void setUp() {
        this.taxaDePagamento= new TaxaDePagamentoSaori();
    }

    @Test
    void deveRetornarCincoPorcentoDoValorDoPedidoEmTaxa(){

        BigDecimal valorPagamento = new BigDecimal("100");

        BigDecimal valorTaxa = valorPagamento.multiply(new BigDecimal("0.05"));

        assertEquals(valorTaxa,this.taxaDePagamento.taxa(valorPagamento));
    }
}