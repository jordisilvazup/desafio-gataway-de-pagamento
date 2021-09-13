package br.com.zup.edu.desafiopagamentos.usuarios;

import br.com.zup.edu.desafiopagamentos.formadepagamento.FormaDePagamento;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<FormaDePagamento> formasDePagamento= new ArrayList<>();

    public Usuario(String nome, String email, FormaDePagamento formaDePagamento) {
        this.nome = nome;
        this.email = email;

    }

    @Deprecated
    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
