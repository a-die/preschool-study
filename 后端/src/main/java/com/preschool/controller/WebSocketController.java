package com.preschool.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.preschool.service.DiscussPostService;
import com.preschool.service.SubjectService;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author websocket服务
 */
@ServerEndpoint(value = "/imserver/{username}")
@Component
public class WebSocketController {
    static SubjectService subjectService;
    static DiscussPostService discussPostService;

    CanalConnector connector;

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public static void setService(SubjectService subjectService,DiscussPostService discussPostService) {
        WebSocketController.subjectService=subjectService;
        WebSocketController.discussPostService=discussPostService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        System.out.println("已和客户端建立连接！");
        sessionMap.put(username, session);
        runCanal();
    }

    @OnMessage
    public void onMessage(String message) {
        //判断数据库被更改的是哪个表
        if(message.equals("discuss_post")){
            sendAllMessage("Hot");
            sendAllMessage("Rank");
            sendAllMessage("Horizon");
            sendAllMessage("Trend");
            sendAllMessage("Header");
        }
        if(message.equals("subject")){
            sendAllMessage("Hot");
            sendAllMessage("Rank");
        }
        if(message.equals("user")){
            sendAllMessage("Horizon");
            sendAllMessage("Map");
            sendAllMessage("Header");
        }
        if(message.equals("comment")){
            sendAllMessage("Trend");
        }
        if(message.equals("comment_reply")){
            sendAllMessage("Trend");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    public void runCanal() {
        // 创建链接
        if(connector==null){
            connector= CanalConnectors.newSingleConnector(new InetSocketAddress("1.117.51.169",
                    11111), "example", "", "");
        }
        int count=0;
        int batchSize = 1000;
        try {
            //建立连接
            connector.connect();
            //设置监听的表
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    count++;
                    System.out.println(count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    //String类型的集合转成String字符串
                    onMessage(StringUtils.join(printEntry(message.getEntries()),","));
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } finally {
            connector.disconnect();
        }
    }
    private static List<String> printEntry(@NotNull List<CanalEntry.Entry> entrys) {
        List<String> list = new ArrayList<>();
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            //做了修改的表 组成一个集合
            list.add(entry.getHeader().getTableName());
        }
        return list;
    }
}

