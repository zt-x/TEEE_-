import com.teee.utils.JWT;
import com.teee.utils.Tencent;
import com.teee.utils.TypeChange;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import org.junit.Test;

import java.util.ArrayList;

public class javaTest {
    @Test
    public void ArrayListAndString() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("你好");
        arr.add("我是");
        arr.add("sb");

        String str = arr.toString();

        for (String s : TypeChange.str2arrl(str)) {
            System.out.println(s);
        }

    }

    @Test
    public void getUID() {
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIwMjAzMTEwMTU1NCwicm9sZSI6InN0dWRlbnQiLCJleHAiOjE2NjgxMjkzODYsImp0aSI6ImQwMjAyZjdmLWYwMjgtNDJkMC05Y2RjLTY1YjdmMWM1Mjg1NCJ9.MmSOm_p6oPGTEC7HTXV6yQdfFYsBiBsUF5p2X9i2Rlg";
        System.out.println(JWT.getUid(token));

    }

    @Test
    public void str(){
        String s = "http://127.0.0.1:8080/face/1670823306430.png";
        System.out.println(s.substring(s.lastIndexOf("/")));
    }

    @Test
    public void base() {
        String imgBase = null;
        try {
            imgBase = TypeChange.getImageBaseURL("http://127.0.0.1:8080/face/1670556470201.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(imgBase);
    }

    @Test
    public void tencentAPI() {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDjU6ZMxWiookaLzXjrsjPtAIG3hEbopWE", "gHi5LQwKWbTAh3DD2YtAFAvY5RcdVBi3");
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
//            String b1 = TypeChange.getImgBase("C:\\Users\\Xuuu\\Pictures\\face\\xzt1.png");
//            String b2 = TypeChange.getImgBase("C:\\Users\\Xuuu\\Pictures\\face\\face3.jpg");
//            req.setImageA(b1);
//            req.setImageB(b2);
            // 返回的resp是一个CompareFaceResponse的实例，与请求对象对应
            CompareFaceResponse resp = client.CompareFace(req);
            // 输出json格式的字符串回包
            System.out.println(CompareFaceResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }


    @Test
    public void tencentTest2(){
        new Tencent().faceCheck("","");
    }
}