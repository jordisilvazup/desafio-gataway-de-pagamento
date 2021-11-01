package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pagamento", cascade = ALL)
    private List<Transacao> transacoes = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime criadoEm = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    private String informacoesExtras;

    private String codigoParaConfirmacaoDePagamento = UUID.randomUUID().toString();


    public Pagamento(String informacoesExtras) {
        this.informacoesExtras = informacoesExtras;
    }

    @Deprecated
    public Pagamento() {
    }

    public String getCodigoParaConfirmacaoDePagamento() {
        return codigoParaConfirmacaoDePagamento;
    }

    public void associar(Transacao transacao) {
        this.transacoes.add(transacao);
    }

    public boolean existeTransacaoIniciada() {
        return this.transacoes.stream().anyMatch(Transacao::aguardandoConfirmacao);
    }

    public boolean concluido() {
        return this.transacoes.stream().anyMatch(Transacao::concluida);
    }

    public void concluir(){
        this.atualizadoEm=LocalDateTime.now();

        Transacao transacao = this.transacoes.stream()
                .filter(Transacao::aguardandoConfirmacao)
                .findAny()
                .get();

        transacao.status(CONCLUIDA);

        associar(transacao);
    }
}
