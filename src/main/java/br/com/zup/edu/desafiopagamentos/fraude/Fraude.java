package br.com.zup.edu.desafiopagamentos.fraude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Fraude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String email;


    public Fraude(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Deprecated
    public Fraude(){}

    public String getEmail() {
        return email;
    }
}
