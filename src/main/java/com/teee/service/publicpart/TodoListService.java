package com.teee.service.publicpart;

import com.teee.domain.returnClass.BooleanReturn;
import org.springframework.stereotype.Service;

/**
 * @author Xu ZhengTao
 */
public interface TodoListService {
    BooleanReturn getTodoList(String token);
}
