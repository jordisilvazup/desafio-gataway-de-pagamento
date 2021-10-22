package br.com.zup.edu.desafiopagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafiopagamentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafiopagamentosApplication.class, args);
    }

}
