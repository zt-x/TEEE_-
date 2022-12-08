package com.teee;

import com.teee.domain.SpecialArray;
import com.teee.utils.SpringBeanUtil;
import com.teee.utils.Tencent;
import com.teee.utils.TypeChange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class UtilTest {

    @Test
    public void tencentTest2(){
        SpringBeanUtil.getBean(Tencent.class).faceCheck("","");
    }


    @Test
    public void arr2str(){
        ArrayList<String> arr = new ArrayList<>();
        arr.add("0.5");
        arr.add("0.8");
        arr.add("1");
        arr.add("null");
        String str = arr.toString();
        ArrayList<String> arrayList = TypeChange.str2arrl(str);
//        arrayList.add("666");
        System.out.println(arrayList.size());
        System.out.println(arrayList);
    }


    @Test
    public void ArrayList2SpecialArrayTest(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("数据一");
        arrayList.add("数据二");
        arrayList.add("数据三");
        arrayList.add("数据四");
        arrayList.add("数据五");
        SpecialArray s = SpecialArray.ArrayList2SpecialArray(arrayList, "-!!!!!!!!!-");
        System.out.println("s=" + s);
        System.out.println("s.size = " + s.size());
    }
}
