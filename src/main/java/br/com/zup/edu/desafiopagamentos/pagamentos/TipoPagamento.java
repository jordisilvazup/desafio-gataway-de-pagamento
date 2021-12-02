package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento.ProcesarPagamentoOffilne;
import br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento.ProcessarPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.processarpagamento.ProcessarPagamentoOnline;

import java.io.Serializable;

public enum TipoPagamento implements Serializable {

    CARTAO(false){
        @Override
        public ProcessarPagamento processarPagamento() {
            return new ProcessarPagamentoOnline();
        }
    },
    DINHEIRO(true){
        @Override
        public ProcessarPagamento processarPagamento() {
            return new ProcesarPagamentoOffilne();
        }
    },
    MAQUINA(true){
        @Override
        public ProcessarPagamento processarPagamento() {
            return new ProcesarPagamentoOffilne();
        }
    },
    CHEQUE(true){
        @Override
        public ProcessarPagamento processarPagamento() {
            return new ProcesarPagamentoOffilne();
        }
    };


    TipoPagamento(boolean disponibilidadeOffline) {
        this.disponibilidadeOffline = disponibilidadeOffline;
    }

    private final boolean disponibilidadeOffline;
    public abstract ProcessarPagamento processarPagamento();

    public boolean isDisponibilidadeOffline() {
        return this.disponibilidadeOffline;
    }
}

