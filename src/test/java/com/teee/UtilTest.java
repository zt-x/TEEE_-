package com.teee;

import com.teee.utils.TypeChange;
import org.junit.Test;


import java.util.ArrayList;

//@SpringBootTest(classes = TEEEApplication.class)
//@RunWith(SpringRunner.class)
public class UtilTest {
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
}
