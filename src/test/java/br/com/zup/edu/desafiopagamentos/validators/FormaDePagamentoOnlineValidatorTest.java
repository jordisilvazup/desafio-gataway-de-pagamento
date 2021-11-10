package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class FormaDePagamentoOnlineValidatorTest {
    @PersistenceContext
    private EntityManager manager;
    private FormaDePagamentoOnlineValidator validator;
    private Errors errors;
    private static final Long IDPEDIDO=1L;
    private static final Long IDUSUARIO=1L;
    private static final Long IDRESTAURANTE=1L;
    private static final Long ID_FORMA_DE_PAGAMENTO_OFFLINE =2L;
    private static final Long ID_FORMA_DE_PAGAMENTO_ONLINE =4L;

    @BeforeEach
    void setUp() {
        this.validator=new FormaDePagamentoOnlineValidator(manager);
    }

    @Test
    void aFormaDePagamentoDeveSerOnline() {
        PagamentoRequest request= new PagamentoRequest(IDPEDIDO,IDRESTAURANTE,IDUSUARIO, ID_FORMA_DE_PAGAMENTO_OFFLINE,"123","123","qualquer informacao extra");

        this.errors = new BeanPropertyBindingResult(request,"request");

        this.validator.validate(request, errors);
        FieldError fieldError = this.errors.getFieldError();

        assertEquals("idFormaPagamento",fieldError.getField());
        assertEquals("A forma de pagamento escolhida não é online.",fieldError.getDefaultMessage());

    }
    @Test
    void oCodigoDeSegurancaNaoDeveSerVazio() {
        PagamentoRequest request= new PagamentoRequest(IDPEDIDO,IDRESTAURANTE,IDUSUARIO, ID_FORMA_DE_PAGAMENTO_ONLINE,"123",null,"qualquer informacao extra");

        this.errors = new BeanPropertyBindingResult(request,"request");

        this.validator.validate(request, errors);
        FieldError fieldError = this.errors.getFieldError();

        assertEquals("codSeguranca",fieldError.getField());
        assertEquals("O codigo de segurança não deve ser vazio.",fieldError.getDefaultMessage());

    }
    @Test
    void oNumeroDoCartaoNaoDeveSerVazio() {
        PagamentoRequest request= new PagamentoRequest(IDPEDIDO,IDRESTAURANTE,IDUSUARIO, ID_FORMA_DE_PAGAMENTO_ONLINE,null,"123","qualquer informacao extra");

        this.errors = new BeanPropertyBindingResult(request,"request");

        this.validator.validate(request, errors);
        FieldError fieldError = this.errors.getFieldError();

        assertEquals("numCartao",fieldError.getField());
        assertEquals("O numero do cartao não deve ser vazio.",fieldError.getDefaultMessage());

    }

}