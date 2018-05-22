package cinnabar.core.component.websocket.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;

import cinnabar.core.component.websocket.constant.Constant;
import cinnabar.core.component.websocket.dto.MessageEntity;
import cinnabar.core.component.websocket.service.WebSocketService;

@Controller
public class WsController {

    @Resource
    WebSocketService webSocketService;

    @MessageMapping(Constant.FORETOSERVERPATH)
    @SendTo(Constant.PRODUCERPATH)
    public MessageEntity send(MessageEntity message) throws Exception {
        List<String> users = Lists.newArrayList();
        users.add("d892bf12bf7d11e793b69c5c8e6f60fb");
        webSocketService.send2Users(users, message);
        return message;
    }
}