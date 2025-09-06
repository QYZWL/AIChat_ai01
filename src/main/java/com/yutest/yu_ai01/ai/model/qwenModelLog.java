package com.yutest.yu_ai01.ai.model;


import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;

import dev.langchain4j.model.chat.listener.ChatModelListener;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "langchain4j.community.dashscope.chat-model")
@Data
public class qwenModelLog {

    private String modelName;

    private String apiKey;

    // 引入监听器
    @Resource
    private ChatModelListener chatModelListener;

    @Bean
    public ChatModel myQwenChatModelLog() {
        return QwenChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .listeners(List.of(chatModelListener)) // 指定监听器
                .build();
    }
}
