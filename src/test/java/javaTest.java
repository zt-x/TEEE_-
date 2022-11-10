import com.teee.utils.JWT;
import com.teee.utils.TypeChange;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class javaTest {
    @Test
    public void ArrayListAndString(){
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
    public void getUID(){
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1aWQiOjIwMjAzMTEwMTU1NCwicm9sZSI6InN0dWRlbnQiLCJleHAiOjE2NjgxMjkzODYsImp0aSI6ImQwMjAyZjdmLWYwMjgtNDJkMC05Y2RjLTY1YjdmMWM1Mjg1NCJ9.MmSOm_p6oPGTEC7HTXV6yQdfFYsBiBsUF5p2X9i2Rlg";
        System.out.println(JWT.getUid(token));

    }
}
