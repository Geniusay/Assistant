package io.github.webscoket.core;


import io.github.common.logger.CommonLogger;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *    Genius
 *   2023/09/06 19:24
 **/
@Component
@ServerEndpoint("/chopperBot")
public class WebSocketHandler {

    @Resource
    CommonLogger log;

    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        // WebSocket 连接建立时执行的逻辑
        log.info("%s connect websocket",session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 处理收到的消息
        log.info("%s send a message:{}",session,message);
        MessageHandlerFactory.doHandler(message,session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        // WebSocket 连接关闭时执行的逻辑
        log.info("{} close connect~",session);
    }

    public void sendMsg(String message) {
        if(sessionMap.containsKey("ChopperBot")){
            Session chopperBot = sessionMap.get("ChopperBot");
            try {
                chopperBot.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String message,String user) {
        if(sessionMap.containsKey(user)){
            Session session = sessionMap.get(user);
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                sessionMap.remove(user);
            }
        }
    }

    public void register(String user,Session session){
        sessionMap.put(user,session);
        log.info("{} {} register websocket",user,session);
    }

    public boolean isRegister(String user){

        return sessionMap.containsKey(user);
    }

    public boolean close(String user){
        if(sessionMap.containsKey(user)){
            Session session = sessionMap.get(user);
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
