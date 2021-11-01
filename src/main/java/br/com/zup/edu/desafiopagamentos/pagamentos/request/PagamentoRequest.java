package br.com.zup.edu.desafiopagamentos.pagamentos.request;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.Pagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.TentativaDeTransacao;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import br.com.zup.edu.desafiopagamentos.validators.ExistId;
import br.com.zup.edu.desafiopagamentos.validators.ExistPedido;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.function.BiFunction;

import static br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao.AGUARDANDO_CONFIRMACAO;

public class PagamentoRequest {
    @ExistPedido
    @NotNull
    private Long idPedido;

    @NotNull
    @ExistId(domainClass = Restaurante.class)
    private Long idRestaurante;

    @NotNull
    @ExistId(domainClass = Usuario.class)
    private Long idUsuario;

    @NotNull
    @ExistId(domainClass = FormaDePagamento.class)
    private Long idFormaPagamento;

    @JsonProperty
    private String informacoesExtras;


    public PagamentoRequest(Long idPedido, Long idRestaurante, Long idUsuario, Long idFormaPagamento) {
        this.idPedido = idPedido;
        this.idRestaurante = idRestaurante;
        this.idUsuario = idUsuario;
        this.idFormaPagamento = idFormaPagamento;
    }


    public Pagamento paraPagamento(BiFunction<Long, Class<?>, Object> find, TentativaDeTransacao tentativaDeTransacao) {

        Restaurante restaurante = (Restaurante) find.apply(idRestaurante, Restaurante.class);

        Usuario usuario = (Usuario) find.apply(idRestaurante, Usuario.class);

        FormaDePagamento formaDePagamento = (FormaDePagamento) find.apply(idRestaurante, FormaDePagamento.class);

        Pagamento novoPagamento = new Pagamento(informacoesExtras);

        Transacao transacao = new Transacao(
                novoPagamento,
                restaurante,
                idPedido,
                tentativaDeTransacao.valorDoPedido(),
                AGUARDANDO_CONFIRMACAO,
                formaDePagamento,
                usuario
        );
        novoPagamento.associar(transacao);
        return novoPagamento;
    }

    public Long getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Long getIdPedido() {
        return idPedido;
    }


}
