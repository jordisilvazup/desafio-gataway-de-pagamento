package br.com.zup.edu.desafiopagamentos.gateways.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VerificacaoDeCartaoResponse {
    @JsonProperty
    public String id;

    @JsonCreator
    public VerificacaoDeCartaoResponse(String id) {
        this.id = id;
    }

    public VerificacaoDeCartaoResponse() {
    }

    public String getId() {
        return id;
    }
}
