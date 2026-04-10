package br.com.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll() // Libera recursos visuais
                        .anyRequest().authenticated() // Bloqueia todo o resto
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true) // Vai para o index após logar
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Criando usuários em memória para teste local

        UserDetails admin = User.builder()
                .username("jorge")
                .password(passwordEncoder().encode("admin123"))
                .roles("DONO")
                .build();

        UserDetails funcionario = User.builder()
                .username("funcionario")
                .password(passwordEncoder().encode("123"))
                .roles("FUNCIONARIO")
                .build();

        return new InMemoryUserDetailsManager(admin, funcionario);
    }
}