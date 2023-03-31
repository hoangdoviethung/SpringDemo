package com.hung.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SeabankMessageCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeabankMessageCache.class);
    private static final String KEY = "SEA_COMMON_MESSAGE";

    private final ObjectMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;

    private List<SeaBankMessageDTO> seaBankMessages = new ArrayList<>();

    public SeabankMessageCache(ObjectMapper mapper,
                               RedisTemplate<String, String> redisTemplate) {
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        new Thread(() -> {
            do {
                try {
                    LOGGER.debug("--- Auto reload cache message ---");
                    syncMessage();
                    Thread.sleep(2 * 60 * 1000);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            } while (true);
        }).start();
    }

    private void syncMessage() {
        try {
            String str = redisTemplate.opsForValue().get(KEY);
            if(ObjectUtils.isEmpty(str)){
                return;
            }
            seaBankMessages = Arrays.asList(mapper.readValue(str, SeaBankMessageDTO[].class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<SeaBankMessageDTO> getSeaBankMessages() {
        return seaBankMessages;
    }
}