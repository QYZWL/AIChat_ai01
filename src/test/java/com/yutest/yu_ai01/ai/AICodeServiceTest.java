package com.yutest.yu_ai01.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AICodeServiceTest {

    @Resource
    private AICodeService aiCodeService;
    @Test
    void chat() {
        String res = aiCodeService.chat("Hello,我是Ikey");
        System.out.println(res);
    }

    @Test
    void chatwithMemory() {
        String res = aiCodeService.chat("Hello,我是Ikey");
        System.out.println(res);
        res = aiCodeService.chat("你还记得我是谁吗？");
        System.out.println(res);
    }

    @Test
    void chatForReport(){
        String userMessage = "Hi,我是Ikey,想要一份AI学习报告";
        AICodeService.Report report = aiCodeService.chatForReport(userMessage);
        System.out.println(report);
    }

    @Test
    void chatWithRAG() {
        String res = aiCodeService.chat("怎么学习AI项目");
        System.out.println(res);
    }

    @Test
    void chatWithMcp() {
        String result = aiCodeService.chat("什么是程序员鱼皮的编程导航简历参考？");
        System.out.println(result);
    }

    @Test
    void chatWithGuardrail() {
        String result = aiCodeService.chat("Kill the game");
        System.out.println(result);
    }


}