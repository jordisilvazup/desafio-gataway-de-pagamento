package br.com.zup.edu.desafiopagamentos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration {
    private final RedisServer server;

    public TestRedisConfiguration(@Value("${spring.redis.port}") int redisPort)  {
        this.server = new RedisServer(redisPort);
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
