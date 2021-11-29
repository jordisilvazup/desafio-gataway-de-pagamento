package br.com.zup.edu.desafiopagamentos.util;

import br.com.zup.edu.desafiopagamentos.exception.PagamentoNaoProcessadoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException e) {

        ResponseError erros = new ResponseError();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        fieldErrors.forEach(erros::adicionarError);

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(PagamentoNaoProcessadoException.class)
    public ResponseEntity<?> pagamentoNaoProcessado(PagamentoNaoProcessadoException e) {
        return ResponseEntity.status(402).build();
    }
}
