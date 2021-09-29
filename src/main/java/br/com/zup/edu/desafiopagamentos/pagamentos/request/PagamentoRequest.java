package br.com.zup.edu.desafiopagamentos.pagamentos.request;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import br.com.zup.edu.desafiopagamentos.validators.ExistId;
import br.com.zup.edu.desafiopagamentos.validators.ExistPedido;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PagamentoRequest {
    @ExistPedido
    @NotNull
    private Long idPedido;

    @NotNull
    @ExistId(domainClass = Restaurante.class)
    private Long idRestaurante;

    @NotNull
    @ExistId(domainClass = Restaurante.class)
    private Long idUsuario;

    @NotNull
    @ExistId(domainClass = Restaurante.class)
    private Long idFormaPagamento;

    @JsonProperty
    private String informacoesExtras;


    public PagamentoRequest(Long idPedido, Long idRestaurante, Long idUsuario, Long idFormaPagamento) {
        this.idPedido = idPedido;
        this.idRestaurante = idRestaurante;
        this.idUsuario = idUsuario;
        this.idFormaPagamento = idFormaPagamento;
    }

    @Transactional
    public Transacao paraTransacao(EntityManager manager, BigDecimal valor) {
        Restaurante restaurante = manager.find(Restaurante.class, idRestaurante);
        Usuario usuario = manager.find(Usuario.class, idUsuario);
        FormaDePagamento formaDePagamento = manager.find(FormaDePagamento.class, idUsuario);

        return new Transacao(restaurante,idPedido, valor,StatusTransacao.AGUARDANDO_CONFIRMACAO,formaDePagamento,usuario,informacoesExtras);

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
