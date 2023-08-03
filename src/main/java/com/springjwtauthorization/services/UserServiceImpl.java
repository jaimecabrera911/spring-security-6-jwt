package com.springjwtauthorization.services;

import com.springjwtauthorization.dtos.UserReqDto;
import com.springjwtauthorization.dtos.UserResDto;
import com.springjwtauthorization.entities.RoleEntity;
import com.springjwtauthorization.entities.UserEntity;
import com.springjwtauthorization.mappers.UserMapper;
import com.springjwtauthorization.repositories.RoleEntityRepository;
import com.springjwtauthorization.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;
    private final RoleEntityRepository roleEntityRepository;

    @Override
    public void save(UserReqDto userReqDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(userReqDto.getPassword());
        userReqDto.setPassword(password);
        UserEntity userEntity = userMapper.toEntity(userReqDto);
        RoleEntity role = roleEntityRepository.findByName("USER").orElseThrow();
        userEntity.setRoles(Set.of(role));
        userEntityRepository.save(userEntity);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getUser;
    }

    @Override
    public UserResDto getUserByUserName(String username) {
        return userMapper.toResDto(getUser(username));
    }

    private UserEntity getUser(String username) {
        return userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
