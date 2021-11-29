package br.com.zup.edu.desafiopagamentos.gateways.clients;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.Pagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import static br.com.zup.edu.desafiopagamentos.gateways.clients.StatusPagamento.*;

public class TentativaPagamentoResponse {
    @JsonProperty
    private String informacaoExtra;
    @JsonProperty
    private StatusPagamento status;

    public TentativaPagamentoResponse(String informacaoExtra, StatusPagamento status) {
        this.informacaoExtra = informacaoExtra;
        this.status = status;
    }

    public TentativaPagamentoResponse() {
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public boolean sucesso() {
        return status.equals(SUCESSO);
    }

    public String getInformacaoExtra() {
        return informacaoExtra;
    }

    public Transacao paraTransacao(BiFunction<Long, Class<?>, Object> find, Pagamento pagamento, PagamentoRequest request, BigDecimal valorCompra) {
        Restaurante restaurante = (Restaurante) find.apply(request.getIdRestaurante(), Restaurante.class);

        Usuario usuario = (Usuario) find.apply(request.getIdUsuario(), Usuario.class);

        FormaDePagamento formaDePagamento = (FormaDePagamento) find.apply(request.getIdFormaPagamento(), FormaDePagamento.class);

        return new Transacao(
                pagamento,
                restaurante,
                request.getIdPedido(),
                valorCompra,
                sucesso()?StatusTransacao.CONCLUIDA:StatusTransacao.FALHA,
                formaDePagamento,
                usuario
        );

    }
}
