package com.yutest.yu_ai01.ai;
/**
 * 与AICodeHelper相比，这种生成式更为简洁
 * 若使用AiService服务对应依赖langchain4j-spring-boot-starter，且在该接口使用@AiService
 * 注意@AiService不与@Configuration同时使用（会存在两个AICodeService例）
 * AiService没有Factory灵活
 */
import com.yutest.yu_ai01.ai.guardrail.guardrailSafe;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import java.util.List;

//@AiService
@InputGuardrails({guardrailSafe.class}) // 输入敏感词检测
public interface AICodeService {

    @SystemMessage(fromResource = "system_Prompt.txt")
    String chat(String userMessage);

    // 多用户之间会话记忆隔离（增加MemoryId）
//    String chat(@MemoryId int memoryId,String userMessage);

    // 结构化输出
    @SystemMessage(fromResource = "system_prompt.txt")
    Report chatForReport(String userMessage);

    // 学习报告
    record Report(String name, List<String> suggestionList){}

    // 流式对话
    @SystemMessage(fromResource = "system_prompt.txt")
    Flux<String> chatFluxStream(@MemoryId int memoryId, @UserMessage String userMessage);


}
