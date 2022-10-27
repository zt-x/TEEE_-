import com.teee.TEEEApplication;
import com.teee.config.Code;
import com.teee.domain.works.BankQuestion;
import com.teee.service.HomeWork.Questions.QuestionBankService;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class service {

    @Test
    public void QuestionBankServiceTest(){
        QuestionBankService questionBankService = SpringBeanUtil.getBean(QuestionBankService.class);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("软件工程");
        arrayList.add("许正韬");

        System.out.println(questionBankService.addBankTags(3, arrayList));

    }

}
