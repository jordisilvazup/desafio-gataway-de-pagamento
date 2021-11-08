package br.com.zup.edu.desafiopagamentos.gateways;

import br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento.RealizarPagamento;
import br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento.RealizarPagamentoSaori;
import br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento.RealizarPagamentoSeya;
import br.com.zup.edu.desafiopagamentos.gateways.realizarPagamento.RealizarPagamentoTango;
import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamento;
import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamentoSaori;
import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamentoSeya;
import br.com.zup.edu.desafiopagamentos.gateways.taxas.TaxaDePagamentoTango;
import org.springframework.stereotype.Component;


public enum GatewayPagamento {
    SAORI(new TaxaDePagamentoSaori()) {
        @Override
        public RealizarPagamento realizarPagamento() {
            return new RealizarPagamentoSaori();
        }
    },

    SEYA(new TaxaDePagamentoSeya()) {
        @Override
        public RealizarPagamento realizarPagamento() {
            return new RealizarPagamentoSeya();
        }
    },

    TANGO(new TaxaDePagamentoTango()) {
        @Override
        public RealizarPagamento realizarPagamento() {
            return new RealizarPagamentoTango();
        }
    };

    GatewayPagamento(TaxaDePagamento taxaDePagamento) {
        this.taxaPagamento = taxaDePagamento;
    }

    public TaxaDePagamento taxaPagamento;

    public abstract RealizarPagamento realizarPagamento();


    public TaxaDePagamento taxaPagamento() {
        return this.taxaPagamento;
    }
}
