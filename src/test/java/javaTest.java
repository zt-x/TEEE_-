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

        for (String s : TypeChange.str_arrl(str)) {
            System.out.println(s);
        }

    }
}
