package com.jiafang.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

public class AliPayUtils {

    public static String pk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static String sk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtpzWwWZ7GYYH9LtSogsmX9rSZwchm8Gn/JA4B2EET9Mp4BSGFI3YBKGfRezqZUwamk8rBYZYHTTumqYTxz5SI5MqsfGdwvciqUY88oBdY6g+edqInGSGi1Wjzm2Kpk5RA6z7VOEyhmyiPTNKZVbRnBEy4Mu6/AP5fisXGDUv/NwIDAQAB";
    private static AlipayClient alipayClient;
    static {
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2016052401437524", sk,"json","UTF-8", pk);
    }

    public static AlipayTradeQueryResponse query(String orderNum){
        int i = 5;
        while (i > 0){
            i--;
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\"" + orderNum + "\"," +
                    "}");
            try {
                AlipayTradeQueryResponse response = alipayClient.execute(request);
                return response;
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static void main(String [] a){
        query("1000000042146695378486047");
    }

    public static AlipayTradeCreateResponse create(String payAccount, String orderNum, String subject, Double totalPrice){
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setBizContent("{" +
                "\"buyer_logon_id\":\"" + payAccount + "\"," +
                "\"out_trade_no\":\"" + orderNum + "\"," +
                "\"subject\":\"" + orderNum + "\"," +
                "\"total_amount\":\"" + orderNum + "\"," +
                "}");
        try {
            AlipayTradeCreateResponse response = alipayClient.execute(request);
            return response;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
