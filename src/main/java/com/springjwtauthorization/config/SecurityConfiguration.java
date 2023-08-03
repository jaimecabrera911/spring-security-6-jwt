package com.springjwtauthorization.config;

import com.springjwtauthorization.entities.RoleEntity;
import com.springjwtauthorization.entities.UserEntity;
import com.springjwtauthorization.jwt.JwtAuthenticationFilter;
import com.springjwtauthorization.repositories.RoleEntityRepository;
import com.springjwtauthorization.repositories.UserEntityRepository;
import com.springjwtauthorization.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    public static final String AUTH_ADMIN = "ADMIN";
    public static final String AUTH_USER = "USER";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/auth/login/**", "/auth/signup/**", "/auth/logout").permitAll()
                                .requestMatchers(POST, "/products/**", "/users/**").hasAnyAuthority(AUTH_ADMIN)
                                .requestMatchers(GET, "/users/**", "/products/**").hasAnyAuthority(AUTH_ADMIN, AUTH_USER)
                                .anyRequest().authenticated())
                //.logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(OK)))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    void createUserAdmin() {
        String password = "$2a$10$zJlMf5GKepVwcZtyaIg5/ed2aJ0PJ0OyHgKbn.afvY7gr2Vm153gW";
        RoleEntity roleEntity = roleEntityRepository.findByName(AUTH_ADMIN).orElseThrow();
        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password(password)
                .email("admin@gmail.om")
                .roles(Set.of(roleEntity))
                .phone("2555555")
                .build();
        Optional<UserEntity> admin1 = userEntityRepository.findByUsername("admin");
        if (admin1.isEmpty()) {
            userEntityRepository.save(admin);
        }
    }
}
