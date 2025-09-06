package com.yutest.yu_ai01.ai;
/**
 * 调用AICodeService接口
 */

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AICodeServiceFactory {

   @Resource
   private ChatModel myQwenChatModelLog; // 自定义的千问模型（加入log）
//    private ChatModel qwenChatModel;


   // 引入RAG
    @Resource
    private ContentRetriever contentRetriever;

    // 引入MCP
    @Resource
    private McpToolProvider mcpToolProvider;

    // 引入Stream
    @Resource
    private StreamingChatModel qwenStreamingChatModel;

    @Bean
    public AICodeService aiCodeService(){
        // 会话记忆
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(5);

        //构造 AI Service
        AICodeService aiCodeService = AiServices.builder(AICodeService.class)
                .chatModel(myQwenChatModelLog)
                .streamingChatModel(qwenStreamingChatModel) // 流式输出
                .chatMemory(chatMemory) //会话记忆
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(5)) // 每个用户会话记忆独立空间
                .contentRetriever(contentRetriever) // RAG检索增强生成
                .toolProvider(mcpToolProvider) // MCP 工具调用

                .build();
        return aiCodeService;

    }
}
