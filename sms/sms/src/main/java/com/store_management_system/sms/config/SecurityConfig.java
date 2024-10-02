package com.store_management_system.sms.config;

import com.store_management_system.sms.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // causing infinite loop since CustomUserDetailsService implements UserDetailsService
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    // () {
    //     return new CustomUserDetailsService();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/login", "/logout","/css/**", "/js/**","/resources/**","Images/**").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/manager/**").hasAuthority("MANAGER")
                    // .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                    .requestMatchers("/register").hasAnyAuthority("ADMIN", "MANAGER")
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
            )
            .logout(logout ->
                logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            ) 
            .userDetailsService(customUserDetailsService);

        return http.build();
    }

    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring().requestMatchers("/resources/**");
    // }
}
