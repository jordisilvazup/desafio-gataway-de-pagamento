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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FormaDePagamentoComumValidatorTest {
    @PersistenceContext
    private EntityManager manager;
    private FormaDePagamentoComumValidator validator;
    private Errors errors;
    private static final Long IDPEDIDO = 1L;
    private static final Long IDUSUARIO = 1L;
    private static final Long IDRESTAURANTE = 1L;
    private static final Long ID_FORMA_DE_PAGAMENTO_EM_COMUM = 1L;
    private static final Long ID_FORMA_DE_PAGAMENTO_NAO_COMUM = 3L;


    @BeforeEach
    void setUp() {
        this.validator = new FormaDePagamentoComumValidator(manager);
    }

    @Test
    @Transactional
    void deveConterAFormaDePagamentoEmComum() {
        PagamentoRequest request = new PagamentoRequest(IDPEDIDO, IDRESTAURANTE, IDUSUARIO, ID_FORMA_DE_PAGAMENTO_EM_COMUM);
        this.errors = new BeanPropertyBindingResult(request, "request");
        validator.validate(request, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    @Transactional
    void naoDeveConterAFormaDePagamentoEmComum() {
        PagamentoRequest request = new PagamentoRequest(IDPEDIDO, IDRESTAURANTE, IDUSUARIO, ID_FORMA_DE_PAGAMENTO_NAO_COMUM);

        this.errors = new BeanPropertyBindingResult(request, "request");

        validator.validate(request, errors);

        List<FieldError> fieldErrors = errors.getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);

        assertEquals(1, fieldErrors.size());
        assertEquals("idFormaPagamento",fieldError.getField());
        assertEquals("O restaurante ou usuario nao possui esta forma de pagamento",fieldError.getDefaultMessage());

    }


}