package br.com.zup.edu.desafiopagamentos.util;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class ExecutorTransacional {
    private final EntityManager manager;

    public ExecutorTransacional(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public <T> T executa(Supplier<T> codigo){
        return codigo.get();
    }

    public EntityManager getManager() {
        return manager;
    }
}
