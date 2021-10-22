package br.com.zup.edu.desafiopagamentos.pagamentos;

import java.math.BigDecimal;

public class TentativaDeTransacao {
    private Long idPedido;
    private BigDecimal valor;

    public TentativaDeTransacao(Long idPedido, BigDecimal valor) {
        this.idPedido=idPedido;
        this.valor=valor;
    }

    public BigDecimal valorDoPedido(){
        return valor;
    }
}
