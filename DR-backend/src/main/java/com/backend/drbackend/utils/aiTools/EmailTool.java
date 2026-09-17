package com.backend.drbackend.utils.aiTools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailTool {
    @Value("${spring.mail.username")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;
    @Tool(description = "完成Email的发送")
    public String sendEmail(
            @ToolParam(description = "收件人邮箱号")String to,
            @ToolParam(description = "邮件标题")String subject,
            @ToolParam(description = "邮件正文")String content){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(content);
                try {
                    javaMailSender.send(message);
                    return "发送成功";
                }catch (Exception e){
                    e.printStackTrace();
                    return "邮件发送失败"+e.getMessage();
                }
    }
}
