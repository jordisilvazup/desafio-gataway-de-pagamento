package br.com.zup.edu.desafiopagamentos.pedidos;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private BigDecimal valor;

    @ManyToOne
    private Restaurante restaurante;

    @ManyToOne
    private FormaDePagamento formaDePagamento;

    @ManyToOne
    private Usuario usuario;

    public Pedido(BigDecimal valor, Restaurante restaurante, FormaDePagamento formaDePagamento, Usuario usuario) {
        this.valor = valor;
        this.restaurante = restaurante;
        this.formaDePagamento = formaDePagamento;
        this.usuario = usuario;
    }

    @Deprecated
    public Pedido() {
    }

    public BigDecimal getValor() {
        return valor;
    }
}
