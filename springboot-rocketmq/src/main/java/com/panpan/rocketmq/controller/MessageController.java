package com.panpan.rocketmq.controller;

import com.panpan.rocketmq.service.RocketMessageSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/16       create this file
 * </pre>
 */
@Slf4j
@Controller
public class MessageController {

    @Autowired
    private RocketMessageSendService rocketMessageSendService;

    @ResponseBody
    @RequestMapping("/sendMessage")
    public String sendMessage(String message) {
        log.info("message:{}", message);
        rocketMessageSendService.sendMessage(message);
        return "success" + message;
    }

}
