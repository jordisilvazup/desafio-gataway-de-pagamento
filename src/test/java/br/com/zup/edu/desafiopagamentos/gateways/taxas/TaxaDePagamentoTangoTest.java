package br.com.zup.edu.desafiopagamentos.gateways.taxas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxaDePagamentoTangoTest {
    private TaxaDePagamentoTango taxaDePagamento;

    @BeforeEach
    void setUp() {
        this.taxaDePagamento=new TaxaDePagamentoTango();
    }

    @Test
    void deveRetornaAQuatrodeTaxaCasoOValorDoPedidoForMenorQue100(){
        BigDecimal valorPagamento = new BigDecimal("100");

        assertEquals(new BigDecimal("4.00"),taxaDePagamento.taxa(valorPagamento));
    }

    @Test
    void deveRetornarSeisPorcentoDoValorDoPagamentoDeTaxaParaPedidoAcimaDeCem(){
        BigDecimal valorPagamento = new BigDecimal("101");

        BigDecimal taxa = valorPagamento.multiply(new BigDecimal("0.06"));

        assertEquals(taxa,taxaDePagamento.taxa(valorPagamento));
    }
}