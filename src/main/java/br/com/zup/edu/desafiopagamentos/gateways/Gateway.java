package br.com.zup.edu.desafiopagamentos.gateways;

import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Optional;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;

@Entity
public class Gateway {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private GatewayPagamento nome;


    @Deprecated
    public Gateway(){}

    public GatewayPagamento getNome() {
        return nome;
    }

    public Optional<TentativaPagamentoResponse> realizarPagamento(PagamentoRequest request, BigDecimal valorCompra){
        return nome.realizarPagamento().realizarPagamento(request,valorCompra);
    }

    public BigDecimal taxa(BigDecimal valor){
        return this.getNome().taxaPagamento.taxa(valor);
    }
}
