package br.com.zup.edu.desafiopagamentos.restaurantes;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.TipoPagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
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

    public List<FormaDePagamento> meiosDePagamentoPara(Usuario usuario, boolean ehFraude){

        Predicate<FormaDePagamento> existeFormaDePagamentoEmComumComUsuario = formaDePagamentoRestaurante -> usuario.getFormasDePagamento()
                .stream()
                .anyMatch(formaDePagamentoUsuario -> formaDePagamentoUsuario.equals(formaDePagamentoRestaurante));
        if(ehFraude){
           return this.formaDePagamentos.stream()
                    .filter(existeFormaDePagamentoEmComumComUsuario)
                    .filter(FormaDePagamento::disponivelOffline)
                   .collect(Collectors.toList());
        }
        return this.formaDePagamentos.stream()
                .filter(existeFormaDePagamentoEmComumComUsuario)
                .collect(Collectors.toList());
    }
}
