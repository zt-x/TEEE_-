package com.teee.utils;

import com.alibaba.fastjson2.JSON;
import com.teee.domain.returnClass.BooleanReturn;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.CompareFaceRequest;
import com.tencentcloudapi.iai.v20200303.models.CompareFaceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Xu ZhengTao
 */
@Component
public class Tencent {
    @Value("${Tencent.secretId}")
    private String secretId;

    @Value("${Tencent.secretKey}")
    private String secretKey;

    /**
     * 人脸识别
     *
     * */
    public BooleanReturn faceCheck(String fileUrl1, String fileUrl2){
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        try {
            Credential cred = new Credential(secretId, secretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-chengdu", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CompareFaceRequest req = new CompareFaceRequest();
            String b1 = TypeChange.getImgBase("C:\\Users\\Xuuu\\Pictures\\face\\xzt1.png");
            String b2 = TypeChange.getImgBase("C:\\Users\\Xuuu\\Pictures\\face\\face3.jpg");
            req.setImageA(b1);
            req.setImageB(b2);
            // 返回的resp是一个CompareFaceResponse的实例，与请求对象对应
            CompareFaceResponse resp = null;
            resp = client.CompareFace(req);

            return new BooleanReturn(true,"",JSON.parse(CompareFaceResponse.toJsonString(resp)));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return new BooleanReturn(false, e.getMessage(), null);
        }
        // 输出json格式的字符串回包
    }




}
