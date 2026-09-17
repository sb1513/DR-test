package com.backend.drbackend.web;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class LlmController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/chat")
    public R txt2txt(@RequestBody Map<String, String> map) {
        try {
            String ans = chatClient.prompt()
                    .advisors(a->a.param(ChatMemory.CONVERSATION_ID, map.get("sid")))
                    .system(map.get("systemPrompt"))
                    .user(map.get("ask"))
                    .call().content();

            return new R (2000, "回答成功", ans);
        }catch (Exception e) {
            e.printStackTrace();
            return new R (5001,"fucku",null);
        }
    }

    @GetMapping("/test")
    public String test() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2184260057@qq.com");
        message.setTo("@qq.com");
        message.setSubject("测试邮件");
        message.setText("这是一封测试邮件");

        mailSender.send(message);

        try {
            mailSender.send(message);
            return "邮件已经提交给SMTP服务器";
        } catch (Exception e) {
            e.printStackTrace();
            return "邮件发送失败：" + e.getMessage();
        }
    }
}