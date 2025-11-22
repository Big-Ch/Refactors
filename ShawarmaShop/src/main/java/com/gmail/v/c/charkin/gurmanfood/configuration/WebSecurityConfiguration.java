package com.gmail.v.c.charkin.gurmanfood.configuration;

import com.gmail.v.c.charkin.gurmanfood.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Конфигурация безопасности Spring Security.
 * Определяет правила авторизации, настройки аутентификации и защиту от атак.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Настраивает правила авторизации и аутентификации.
     *
     * @param http объект HttpSecurity для настройки
     * @throws Exception при ошибке конфигурации
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    // Публичные эндпоинты
                    .antMatchers("/",
                            "/registration/**",
                            "/user/contacts",
                            "/img/**",
                            "/static/**",
                            "/auth/**",
                            "/menu/**",
                            "/shawarma/**",
                            "/login",
                            "/error"
                    ).permitAll()
                    // Админские эндпоинты требуют роль ADMIN
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    // Все остальные запросы требуют аутентификации
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/user/account", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                    .logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                    // Защита от CSRF включена по умолчанию
                    .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                .and()
                    .sessionFixation().migrateSession()
                .and()
                    // Защита от clickjacking и других атак
                    .headers()
                    .frameOptions().deny()
                    .contentTypeOptions();
    }

    /**
     * Настраивает менеджер аутентификации.
     *
     * @param auth объект AuthenticationManagerBuilder для настройки
     * @throws Exception при ошибке конфигурации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Создает бин AuthenticationManager для использования в других компонентах.
     *
     * @return AuthenticationManager
     * @throws Exception при ошибке создания
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}