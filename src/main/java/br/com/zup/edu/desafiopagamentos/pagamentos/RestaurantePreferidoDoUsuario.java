package br.com.zup.edu.desafiopagamentos.pagamentos;

import br.com.zup.edu.desafiopagamentos.pagamentos.response.FormasDePagamentoResponse;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

import static br.com.zup.edu.desafiopagamentos.util.PropriedadeCache.DEFAULT_TIMING;

@RedisHash(value = "restaurante-preferido-usuario", timeToLive = DEFAULT_TIMING)
public class RestaurantePreferidoDoUsuario implements Serializable {

    @Id
    private String id;
    private int quantidadeAcesso;
    private List<FormasDePagamentoResponse> formasDePagamento;

    public RestaurantePreferidoDoUsuario(String id, List<FormasDePagamentoResponse> formasDePagamento) {
        this.id = id;
        this.formasDePagamento = formasDePagamento;
        this.quantidadeAcesso = 1;
    }

    public void incrementarAcesso() {
        this.quantidadeAcesso += 1;
    }

    public boolean aptoACache() {
        return quantidadeAcesso >= 10;
    }

    public List<FormasDePagamentoResponse> formasDePagamento() {
        return this.formasDePagamento;
    }

}
