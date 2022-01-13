package br.com.zup.edu.desafiopagamentos.usuarios;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JoinTable(indexes = {@Index(name = "usuario_id", columnList = "usuario_id")})
    private List<FormaDePagamento> formaDePagamentos = new ArrayList<>();

    public Usuario(String nome, String email, FormaDePagamento formaDePagamento) {
        this.nome = nome;
        this.email = email;

    }

    @Deprecated
    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<FormaDePagamento> getFormaDePagamentos() {
        return formaDePagamentos;
    }

    public boolean aceita(FormaDePagamento formaDePagamento) {
        return this.getFormaDePagamentos().contains(formaDePagamento);
    }

}
