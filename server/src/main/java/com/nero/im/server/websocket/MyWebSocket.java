package com.nero.im.server.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nero.im.server.domain.RequestResult;
import com.nero.im.server.domain.rm.RightModule;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/websocket")
@Component
@Scope("prototype")
public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    private static List<RightModule> rightModuleList = new ArrayList<RightModule>() {
        {
            add(new RightModule(1, "rm1"));
            add(new RightModule(2, "rm2"));
            add(new RightModule(3, "rm3"));
            add(new RightModule(4, "rm4"));
            add(new RightModule(5, "rm5"));
            add(new RightModule(6, "rm6"));
            add(new RightModule(7, "rm7"));
        }
    };

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {

            sendMessage("欢迎加入");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws JsonProcessingException, InterruptedException {
        System.out.println("来自客户端的消息:" + message);
        if (message.equals("/rightModule/")) {
            Random rand = new Random();

            // 死循环 查询有无数据变化
            while (true) {
                Thread.sleep(300); // 休眠300毫秒，模拟处理业务等
                int i = rand.nextInt(100); // 产生一个0-100之间的随机数
                if (i > 20 && i < 56) { // 如果随机数在20-56之间就视为有效数据，模拟数据发生变化
                    RequestResult result = new RequestResult();
                        result.setErrorNum("1000");

                        result.setResult(rightModuleList);

                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        this.sendMessage(objectMapper.writeValueAsString(result));
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else { // 模拟没有数据变化，将休眠 hold住连接
                    Thread.sleep(1300);
                }
            }

        }
//        //群发消息
//        for (MyWebSocket item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


    // 发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
