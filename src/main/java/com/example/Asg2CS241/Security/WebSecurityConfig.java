package com.example.Asg2CS241.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomStudentDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        // Apply the custom authentication provider
        http.authenticationProvider(authenticationProvider());

        // Define your security rules
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/courseAdmin/**").hasRole("ADMIN")
                        .requestMatchers("/parent/**").hasRole("PARENT")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/courseInstructor/**").hasRole("INSTRUCTOR")
                        .anyRequest().permitAll()  // Permit all other requests
                )
                .formLogin(login -> login
                        .loginPage("/login-student") // Specify the default login page
                        .usernameParameter("email")
                        .defaultSuccessUrl("/StudentDashboard")
                        //.successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login-parent") // Specify the parent login page
                        .usernameParameter("email")
                        .defaultSuccessUrl("/ParentDashboard")
                        //.successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login-instructor") // Specify the instructor login page
                        .usernameParameter("email")
                        .defaultSuccessUrl("/CourseInstructorDashboard")
                        ///.successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login-admin") // Specify the admin login page
                        .usernameParameter("email")
                        .defaultSuccessUrl("/CourseAdminDashboard")
                        //.successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/")  // Redirect to home page after logout
                        .permitAll()
                );

        return http.build();
    }
}
