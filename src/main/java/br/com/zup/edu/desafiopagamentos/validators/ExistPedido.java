package br.com.zup.edu.desafiopagamentos.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistPedidoValidator.class)
public @interface ExistPedido {

    String message() default "Não existe um registro relacionado a este identificador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
