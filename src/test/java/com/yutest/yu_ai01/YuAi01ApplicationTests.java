package com.yutest.yu_ai01;

import com.yutest.yu_ai01.ai.AICodeHelper;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YuAi01ApplicationTests {

    @Resource
    private AICodeHelper aiCodeHelper;


    @Test
    void chat(){
        aiCodeHelper.chat("你好，我是IKEY");
    }

    @Test
    void chatUserMessage(){
        UserMessage userMessage = UserMessage.from(
                TextContent.from("描述图片"),
                ImageContent.from("https://blog.csdn.net/weixin_68205197?spm=1000.2115.3001.5343")
        );
        aiCodeHelper.chatUserMessage(userMessage);
    }

}
