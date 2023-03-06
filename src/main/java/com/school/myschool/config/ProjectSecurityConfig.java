package com.school.myschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig  {

        @Bean
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            // Permit All Requests inside the Web Application

                http.csrf().ignoringAntMatchers("/saveMsg").ignoringAntMatchers("/public/**").
                        ignoringAntMatchers("/api/**").ignoringAntMatchers("/data-api/**").and()
                        .authorizeRequests()
                    .mvcMatchers("/dashboard").authenticated()
                    .mvcMatchers("/displayProfile").authenticated()
                    .mvcMatchers("/updateProfile").authenticated()
                    .mvcMatchers("/student/**").hasRole("STUDENT")
                    .mvcMatchers("/displayMessages").hasRole("ADMIN")
                    .mvcMatchers("/admin/**").hasRole("ADMIN")
                    .mvcMatchers("/api/**").authenticated()
                    .mvcMatchers("/data-api/**").authenticated()
                    .mvcMatchers("/home").permitAll()
                    .mvcMatchers("/holidays/**").permitAll()
                    .mvcMatchers("/contact").permitAll()
                    .mvcMatchers("/courses").permitAll()
                    .mvcMatchers("/about").permitAll()
                    .mvcMatchers("/login").permitAll()
                    .mvcMatchers("/saveMsg").permitAll()
                    .mvcMatchers("/public/**").permitAll().
                    and().formLogin().loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                        .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                    .and().httpBasic();


            /*Deny All Requests inside the Web Application
                    http.authorizeRequests().anyRequest().denyAll().
                            and().formLogin()
                            .and().httpBasic();*/

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .roles("USER")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("54321")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

}
