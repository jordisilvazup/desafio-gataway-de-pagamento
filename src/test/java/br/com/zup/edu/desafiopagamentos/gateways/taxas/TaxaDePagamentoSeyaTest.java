package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxaDePagamentoSeyaTest {
    private TaxaDePagamentoSeya taxaDePagamento;


    @BeforeEach
    void setUp() {
        this.taxaDePagamento=new TaxaDePagamentoSeya();
    }

    @Test
    void deveRetornaSeisDeTaxaDePagamento(){
        BigDecimal valorPagamento = new BigDecimal("23.0");
        assertEquals(new BigDecimal("6.00"),this.taxaDePagamento.taxa(valorPagamento));
    }
}