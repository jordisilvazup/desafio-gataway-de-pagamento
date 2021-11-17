package br.com.zup.edu.desafiopagamentos.gateways;

import br.com.zup.edu.desafiopagamentos.gateways.clients.TentativaPagamentoResponse;
import br.com.zup.edu.desafiopagamentos.pagamentos.ProcessaPagamento;
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

    public Optional<TentativaPagamentoResponse> realizarPagamento(ProcessaPagamento processaPagamento){
        return nome.realizarPagamento().realizarPagamento(processaPagamento);
    }

    public BigDecimal taxa(BigDecimal valor){
        return this.getNome().taxaPagamento.taxa(valor);
    }
}
