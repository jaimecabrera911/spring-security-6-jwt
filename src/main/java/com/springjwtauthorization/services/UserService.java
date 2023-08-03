package com.springjwtauthorization.services;

import com.springjwtauthorization.dtos.UserReqDto;
import com.springjwtauthorization.dtos.UserResDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void save(UserReqDto userReqDto);

    UserDetailsService userDetailsService();

    UserResDto getUserByUserName(String username);
}
