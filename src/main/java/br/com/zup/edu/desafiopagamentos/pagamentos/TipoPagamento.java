package br.com.zup.edu.desafiopagamentos.pagamentos;

public enum TipoPagamento {
    CARTAO{
        @Override
        public boolean disponibilidadeOffline() {
            return false;
        }
    },DINHEIRO{
        @Override
        public boolean disponibilidadeOffline() {
            return true;
        }
    },MAQUINA {
        @Override
        public boolean disponibilidadeOffline() {
            return true;
        }
    },CHEQUE {
        @Override
        public boolean disponibilidadeOffline() {
            return true;
        }
    };

    public abstract boolean disponibilidadeOffline();
}

