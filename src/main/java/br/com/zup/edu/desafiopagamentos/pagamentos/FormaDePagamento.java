package br.com.zup.edu.desafiopagamentos.pagamentos;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Entity
@Cacheable
public class FormaDePagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(STRING)
    private TipoPagamento tipo;

    public FormaDePagamento(String descricao, TipoPagamento tipo) {
        this.descricao = descricao;
        this.tipo = tipo;
    }

    @Deprecated
    public FormaDePagamento() {
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getId() {
        return id;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public boolean disponivelOffline() {
        return tipo.isDisponibilidadeOffline();
    }

    @Override
    public String toString() {
        return "FormaDePagamento{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
