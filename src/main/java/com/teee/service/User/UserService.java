package com.teee.service.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean isUserExist(Long uid);
}
