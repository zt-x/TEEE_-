package com.teee.serviceTest;

import com.teee.TEEEApplication;
import com.teee.config.Code;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.service.publicpart.TodoListService;
import com.teee.utils.JWT;
import com.teee.utils.SpringBeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TEEEApplication.class)
@RunWith(SpringRunner.class)
public class TodoListTest {
    @Test
    public void teacherTodolistTest(){
        TodoListService tod = SpringBeanUtil.getBean(TodoListService.class);
        BooleanReturn todoList = tod.getTodoList(JWT.jwtEncrypt(202031101668l, Code.Student));
        System.out.println(todoList.getData());
        System.out.println(todoList.getMsg());
    }
}
