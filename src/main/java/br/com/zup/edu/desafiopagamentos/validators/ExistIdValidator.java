package br.com.zup.edu.desafiopagamentos.validators;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistIdValidator implements ConstraintValidator<ExistId,Long> {
    private final EntityManager manager;
    private Class<?> domainClass;


    public ExistIdValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(ExistId constraintAnnotation) {
        this.domainClass=constraintAnnotation.domainClass();
    }


    @Override
    @Transactional
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return manager.find(domainClass,id)!=null;
    }
}
