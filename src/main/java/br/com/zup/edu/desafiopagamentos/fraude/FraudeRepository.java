package br.com.zup.edu.desafiopagamentos.fraude;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudeRepository extends JpaRepository<Fraude,Long> {

    boolean existsByEmail(String email);
}
