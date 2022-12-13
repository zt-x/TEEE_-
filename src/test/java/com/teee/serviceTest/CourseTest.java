package com.teee.serviceTest;


import com.teee.TEEEApplication;
import com.teee.service.Course.CourseService;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class CourseTest {
    @Test
    public void zipFile(){
        CourseService bean = SpringBeanUtil.getBean(CourseService.class);
        bean.packageFile(47);
    }
}
