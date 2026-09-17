package com.backend.drbackend.utils.aiTools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTool {
    @Tool(description = "获取现在的日期和时间")
    public String getNow(){
        Date date = new Date();
        return "今天是"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
