package br.com.zup.edu.desafiopagamentos.validators;

import br.com.zup.edu.desafiopagamentos.usuarios.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest()
class ExistIdValidatorTest {
    @PersistenceContext
    private EntityManager manager;
    @Mock
    private ConstraintValidatorContext context;

    private ExistIdValidator validator;

    private ExisteIdValidatorTestCase testCase;

    @BeforeEach
    void setUp() {
        this.validator = new ExistIdValidator(manager);
        this.testCase = new ExisteIdValidatorTestCase();
    }

    @Test
    void existeUmRegistroParaEsteId() {


        this.validator.initialize(testCase);

        boolean valid = this.validator.isValid(1L, context);

        assertTrue(valid);
    }

    @Test
    void naoExisteUmRegistroParaEsteId() {

        this.validator.initialize(testCase);

        boolean valid = this.validator.isValid(1000L, context);

        assertFalse(valid);
    }

    public static class ExisteIdValidatorTestCase implements ExistId {

        @Override
        public String message() {
            return null;
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<?> domainClass() {
            return Usuario.class;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ExistId.class;
        }
    }
}

