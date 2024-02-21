package com.bank.payment.auctions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class AuctionsEventListenerTest {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @MockBean
    AuctionsEventListener eventListener;
    @Autowired
    ObjectMapper objectMapper;
    @Container
    static RedisContainer REDIS_CONTAINER =
            new RedisContainer(DockerImageName.parse("myredis")).withExposedPorts(6379);
    @DynamicPropertySource
     static void registerRedisProperties(DynamicPropertyRegistry registry){
        registry.add("spring.data.redis.host",REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port",()->REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @Test
    void onMessage() throws Exception{
        AuctionModel auctionModel =AuctionModel.builder()
                .auctionId("1")
                .userId("3")
                .currencyTo("USD")
                .currencyType("EGP")
                .currentAmount(22200L)
                .createdAt(new Date())
                .build();
        redisTemplate.convertAndSend("auction-events",auctionModel);
        Thread.sleep(1000);
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(eventListener).onMessage(argumentCaptor.capture(), ArgumentMatchers.any());
        AuctionModel auctionModel1 =objectMapper.readValue(argumentCaptor.getValue().getBody(), AuctionModel.class);
        assertEquals(auctionModel1.getAuctionId(),auctionModel.getAuctionId());

    }
}