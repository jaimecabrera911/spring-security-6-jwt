package com.springjwtauthorization.controller;

import com.springjwtauthorization.dtos.AuthRequest;
import com.springjwtauthorization.dtos.AuthResponse;
import com.springjwtauthorization.dtos.ProductReqDto;
import com.springjwtauthorization.dtos.ProductResDto;
import com.springjwtauthorization.dtos.UserReqDto;
import com.springjwtauthorization.dtos.UserResDto;
import com.springjwtauthorization.entities.UserEntity;
import com.springjwtauthorization.jwt.JwtService;
import com.springjwtauthorization.services.ProductService;
import com.springjwtauthorization.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;
    private final ProductService productService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            UserEntity user = (UserEntity) authentication.getPrincipal();
            String accessToken = jwtService.generateToken(user);
            return AuthResponse.builder().username(request.getUsername()).accessToken(accessToken).build();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody UserReqDto userReqDto) {
        userService.save(userReqDto);
    }

    @GetMapping("/products")
    public List<ProductResDto> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void getProducts(@RequestBody ProductReqDto productReqDto) {
        productService.saveProduct(productReqDto);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public UserResDto getUser(@RequestHeader String authorization) {
        String token = getToken(authorization);
        String userName = jwtService.extractUserName(token);
        return userService.getUserByUserName(userName);
    }

    private static String getToken(String authorization) {
        return authorization.replace("Bearer ", "");
    }
}
