package br.com.zup.edu.desafiopagamentos.formadepagamento;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Entity
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

    @Override
    public String toString() {
        return "FormaDePagamento{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
