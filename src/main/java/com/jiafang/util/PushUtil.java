package com.jiafang.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.jiafang.model.PushType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by lhxia on 16/6/19.
 */
public class PushUtil {

    static JPushClient jpushClient = new JPushClient("4e6677d392cc517f210c8942", "4f3fab1e1ba1ff984e247ff8");
    static JPushClient jpushClientKangFuck = new JPushClient("198163c74e806b5893650857", "25fd97f72bc499c5ed74f6d1");

    static JPushClient[] pushClients = new JPushClient[]{jpushClient, jpushClientKangFuck};

    public static void newOrder(Integer userId, Integer orderId) {

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId.toString());//order id
        push(new String[]{userId.toString()}, "您有一个新的订单，点击查看。", "新订单", "您有一个新的订单，点击查看。", PushType.NEW_ORDER, map);
    }

    public static void cancelOrder(Integer userId, Integer orderId, String nickname) {

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId.toString());//order id
        push(new String[]{userId.toString()}, "您的订单已被" + nickname + "取消。", "取消订单", "您的订单已被" + nickname + "取消。", PushType.CANCEL_ORDER, map);
    }

    public static void payOrder(Integer userId, Integer orderId, String nickname) {

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId.toString());//order id
        push(new String[]{userId.toString()}, "您的订单已由" + nickname + "完成付款，请尽快发货。", "订单已支付", "您的订单已由" + nickname + "完成付款，请尽快发货。", PushType.PAY_ORDER, map);
    }

    public static void newBind(Integer userId, Integer companyUserId) {

        Map<String, String> map = new HashMap<>();
        map.put("userId", userId.toString());//客户ID
        push(new String[]{companyUserId.toString()}, "您有一个新的客户，点击查看。", "新的客户", "您有一个新的客户，点击查看。", PushType.NEW_BIND, map);
    }

    public static void sendOrder(Integer userId, Integer orderId, String orderTitle, String nickname) {

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId.toString());//order id
        push(new String[]{userId.toString()}, "您购买的" + orderTitle + "已由卖家" + nickname + "发货。", "已发货", "您购买的" + orderTitle + "已由卖家" + nickname + "发货。", PushType.SEND_ORDER, map);
    }

    public static void receiveOrder(Integer userId, Integer orderId, String orderTitle, String nickname) {

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId.toString());//order id
        push(new String[]{userId.toString()}, "买家" + nickname + "已确认收到您的商品" + orderTitle + "。", "确认收货", "买家" + nickname + "已确认收到您的商品" + orderTitle + "。", PushType.RECEIVE_ORDER, map);
    }


    public static void push(String[] targetId, String alert, String title, String message, PushType type, Map<String, String> extras) {

        IosNotification.Builder builder = IosNotification.newBuilder()
                .setAlert(alert)
                .disableBadge()
                .setContentAvailable(true)
                .addExtra("t", type.ordinal()); //push type

        if (extras == null){
            extras = new HashMap<>();
        }
        Set<Map.Entry<String, String>> entries = extras.entrySet();
        Iterator<Map.Entry<String, String>> it = entries.iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            builder.addExtra(entry.getKey(), entry.getValue());
        }

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.newBuilder().setMessage(Message.newBuilder().setMsgContent(message).setTitle(title).addExtra("xx", "ddd").build())
                .setPlatform(cn.jpush.api.push.model.Platform.all())
                .setAudience(Audience.alias(targetId)).setNotification(Notification.newBuilder()
                        .addPlatformNotification(builder.build()).addPlatformNotification(AndroidNotification.alert(alert))
                        .build())
                .build();

        for (JPushClient client : pushClients){
            try {
                PushResult result = client.sendPush(payload);
                System.out.println("push --> " + result);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
    //			LOG.error("Connection error, should retry later", e);
                e.printStackTrace();

            } catch (APIRequestException e) {
                e.printStackTrace();
                // Should review the error, and fix the request
    //			LOG.error("Should review the error, and fix the request", e);
    //			LOG.info("HTTP Status: " + e.getStatus());
    //			LOG.info("Error Code: " + e.getErrorCode());
    //			LOG.info("Error Message: " + e.getErrorMessage());
                System.out.println("push --> " + e.getErrorMessage());
            }
        }
    }

    private static JPushClient getClient(int index){

        return pushClients[index];
    }
}
