package br.com.zup.edu.desafiopagamentos.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ResponseError {

    @JsonProperty
    private List<String> messages = new ArrayList<>();

    public void adicionarError(FieldError fieldError) {
        messages.add(String.format("O %s %s", fieldError.getField(), fieldError.getDefaultMessage()));
    }

    public void adicionarError(String campo, String mensagem) {
        messages.add(String.format("O %s %s", campo, mensagem));
    }
}
