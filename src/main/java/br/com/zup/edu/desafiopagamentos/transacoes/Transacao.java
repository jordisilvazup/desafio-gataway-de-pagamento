package br.com.zup.edu.desafiopagamentos.transacoes;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.Pagamento;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao.*;

@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @JoinColumn(unique = true)
    private Long pedido;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaDePagamento formaDePagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    private Pagamento pagamento;


    private LocalDateTime criadoEm = LocalDateTime.now();

    public Transacao(Pagamento pagamento, Restaurante restaurante, Long pedido, BigDecimal valor, StatusTransacao status, FormaDePagamento formaDePagamento, Usuario usuario) {
        this.pagamento = pagamento;
        this.restaurante = restaurante;
        this.pedido = pedido;
        this.valor = valor;
        this.status = status;
        this.formaDePagamento = formaDePagamento;
        this.usuario = usuario;
    }

    @Deprecated
    public Transacao() {
    }

    public void status(StatusTransacao status) {
        this.status = status;
    }

    public boolean aguardandoConfirmacao() {
        return status.equals(AGUARDANDO_CONFIRMACAO);
    }

    public boolean concluida() {
        return status.equals(CONCLUIDA);
    }

    public boolean falha() {
        return status.equals(FALHA);
    }


}
