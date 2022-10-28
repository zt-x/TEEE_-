import com.teee.TEEEApplication;
import com.teee.config.Code;
import com.teee.controller.publicpart.PowerController;
import com.teee.controller.publicpart.Work.WorkController;
import com.teee.dao.CourseDao;
import com.teee.domain.Course;
import com.teee.domain.works.BankQuestion;
import com.teee.service.HomeWork.Questions.QuestionBankService;
import com.teee.service.publicpart.PowerService;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.tree.ExpandVetoException;
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
    @Autowired
    WorkController workController;

    @Test
    public void getAllWorksByCIDTest(){
        workController.getAllWorksByCID(15);
    }

    @Test
    public void daoTest(){
        CourseDao courseDao = SpringBeanUtil.getBean(CourseDao.class);
        try{
            Course course = courseDao.selectById(26);
            if(course == null){
                System.out.println("Course is null");
            }
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }

}
