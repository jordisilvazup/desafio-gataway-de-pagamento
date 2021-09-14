package br.com.zup.edu.desafiopagamentos.restaurantes;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<FormaDePagamento> formaDePagamentos=new ArrayList<>();

    public Restaurante(String nome, List<FormaDePagamento> formaDePagamentos) {
        this.nome = nome;
        this.formaDePagamentos = formaDePagamentos;
    }

    @Deprecated
    public Restaurante(){}

    public List<FormaDePagamento> getFormaDePagamentos() {
        return formaDePagamentos;
    }
}
