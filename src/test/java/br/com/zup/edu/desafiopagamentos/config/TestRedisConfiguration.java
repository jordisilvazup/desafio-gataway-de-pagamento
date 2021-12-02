package br.com.zup.edu.desafiopagamentos.config;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration {
    private RedisServer server;

    public TestRedisConfiguration() {
        this.server = new RedisServer(8888);
    }

    @PostConstruct
    public void start(){
        this.server.start();
    }

    @PreDestroy
    public void stop(){
        this.server.stop();
    }
}
