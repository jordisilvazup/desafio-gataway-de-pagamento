package br.com.zup.edu.desafiopagamentos.pagamentos.request;

import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import br.com.zup.edu.desafiopagamentos.validators.ExistId;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultarFormasDePagamentoEmComumRequest {
    @ExistId(domainClass = Usuario.class)
    @JsonProperty
    private Long idUsuario;
    @ExistId(domainClass = Restaurante.class)
    @JsonProperty
    private Long idRestaurante;

    public ConsultarFormasDePagamentoEmComumRequest(Long idUsuario, Long idRestaurante) {
        this.idUsuario = idUsuario;
        this.idRestaurante = idRestaurante;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }
}
