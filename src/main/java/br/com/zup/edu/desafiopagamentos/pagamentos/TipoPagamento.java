package br.com.zup.edu.desafiopagamentos.pagamentos;

public enum TipoPagamento {

    CARTAO(false),
    DINHEIRO(true),
    MAQUINA(true),
    CHEQUE(true);


    TipoPagamento(boolean disponibilidadeOffline) {
        this.disponibilidadeOffline = disponibilidadeOffline;
    }

    private final boolean disponibilidadeOffline;

    public boolean isDisponibilidadeOffline() {
        return this.disponibilidadeOffline;
    }
}

