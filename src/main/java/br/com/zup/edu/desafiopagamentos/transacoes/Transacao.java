package br.com.zup.edu.desafiopagamentos.transacoes;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    private String informacoesExtras;

    private String codigoParaConfirmacaoDePagamento= UUID.randomUUID().toString();

    private LocalDateTime criadoEm = LocalDateTime.now();

    public Transacao(Restaurante restaurante, Long pedido,BigDecimal valor ,StatusTransacao status, FormaDePagamento formaDePagamento,Usuario usuario ,String informacoesExtras) {
        this.restaurante = restaurante;
        this.pedido = pedido;
        this.valor=valor;
        this.status = status;
        this.formaDePagamento = formaDePagamento;
        this.usuario=usuario;
        this.informacoesExtras = informacoesExtras;
    }

    @Deprecated
    public Transacao() {
    }


    public String getCodigoParaConfirmacaoDePagamento() {
        return codigoParaConfirmacaoDePagamento;
    }
}
