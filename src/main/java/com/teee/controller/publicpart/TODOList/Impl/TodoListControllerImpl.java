package com.teee.controller.publicpart.TODOList.Impl;

import com.teee.config.Code;
import com.teee.controller.publicpart.TODOList.TodoListController;
import com.teee.domain.returnClass.BooleanReturn;
import com.teee.domain.returnClass.Result;
import com.teee.service.publicpart.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Xu ZhengTao
 */
@Controller
@ResponseBody
@RequestMapping("/TodoList")
public class TodoListControllerImpl implements TodoListController {

    @Autowired
    TodoListService todoListService;

    @Override
    @RequestMapping("/getTodoList")
    public Result getTodoList(@RequestHeader("Authorization") String token) {
        BooleanReturn todoList = todoListService.getTodoList(token);
        if(todoList.isSuccess()){
            return new Result(Code.Suc,todoList.getData(),todoList.getMsg());
        }else {
            return new Result(Code.ERR,null,todoList.getMsg());
        }
    }
}
