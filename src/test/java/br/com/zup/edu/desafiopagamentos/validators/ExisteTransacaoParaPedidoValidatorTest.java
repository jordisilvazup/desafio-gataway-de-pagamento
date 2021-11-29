package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.pagamentos.FormaDePagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.Pagamento;
import br.com.zup.edu.desafiopagamentos.pagamentos.request.PagamentoRequest;
import br.com.zup.edu.desafiopagamentos.restaurantes.Restaurante;
import br.com.zup.edu.desafiopagamentos.transacoes.StatusTransacao;
import br.com.zup.edu.desafiopagamentos.transacoes.Transacao;
import br.com.zup.edu.desafiopagamentos.transacoes.TransacaoRepository;
import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ExisteTransacaoParaPedidoValidatorTest {
    @Autowired
    private TransacaoRepository transacaoRepository;
    @PersistenceContext
    private EntityManager manager;
    private ExisteTransacaoParaPedidoValidator validator;
    private Restaurante restaurante;
    private FormaDePagamento formaDePagamento;
    private Usuario usuario;
    private final static Long ID_PEDIDO = 2L;
    private final static Long ID_RESTAURANTE = 1L;
    private final static Long ID_FORMA_DE_PAGAMENTO = 1L;
    private final static Long ID_USUARIO = 1L;

    private Errors errors;

    @BeforeEach
    void setUp() {
        this.validator = new ExisteTransacaoParaPedidoValidator(transacaoRepository, manager);
        this.usuario = manager.find(Usuario.class, ID_USUARIO);
        this.restaurante = manager.find(Restaurante.class, ID_RESTAURANTE);
        this.formaDePagamento = manager.find(FormaDePagamento.class, ID_FORMA_DE_PAGAMENTO);
    }

    @AfterEach
    void tearDown() {
        this.transacaoRepository.deleteAll();
    }

    @Test
    void deveExistirUmaTransacaoParaEstePedido() {
        Pagamento novoPagamento= new Pagamento(null);
        manager.persist(novoPagamento);
        Transacao transacaoExistente = new Transacao(
                novoPagamento,
                restaurante,
                ID_PEDIDO,
                new BigDecimal("1.0"),
                StatusTransacao.AGUARDANDO_CONFIRMACAO,
                formaDePagamento,
                usuario
        );

        transacaoRepository.save(transacaoExistente);
        novoPagamento.associar(transacaoExistente);

        PagamentoRequest request= new PagamentoRequest(ID_PEDIDO,ID_RESTAURANTE,ID_USUARIO,ID_FORMA_DE_PAGAMENTO);

        this.errors = new BeanPropertyBindingResult(request, "request");

        this.validator.validate(request, errors);

        List<FieldError> fieldErrors = errors.getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);

        assertEquals(1, fieldErrors.size());
        assertEquals("idPedido",fieldError.getField());
        assertEquals("ja existe uma transacao iniciada para este pedido.",fieldError.getDefaultMessage());


    }

    @Test
    void naoDeveExistirUmaTransacaoParaEstePedido() {

        PagamentoRequest request= new PagamentoRequest(ID_PEDIDO,ID_RESTAURANTE,ID_USUARIO,ID_FORMA_DE_PAGAMENTO);

        this.errors = new BeanPropertyBindingResult(request, "request");

        this.validator.validate(request, errors);

        assertFalse(errors.hasErrors());

    }
}