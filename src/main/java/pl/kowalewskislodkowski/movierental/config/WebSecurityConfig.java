package pl.kowalewskislodkowski.movierental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.kowalewskislodkowski.movierental.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers("/h2-console/*").permitAll()
            .and()
            .authorizeRequests().antMatchers("/").permitAll()
            .and()
            .authorizeRequests().antMatchers("/images/*").permitAll()
            .and()
            .authorizeRequests().antMatchers(HttpMethod.GET, "/films").permitAll()
            .and()
            .authorizeRequests().antMatchers(HttpMethod.GET, "/films/*").permitAll()
            .and()
            .authorizeRequests().anyRequest().authenticated()
            // .and()
            // .authorizeRequests().anyRequest().rememberMe()
            .and()
            .formLogin()
            .loginPage("/login").loginProcessingUrl("/perform_login").defaultSuccessUrl("/")
            .permitAll()
            .and()
            .logout().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .userDetailsService(userDetailsService);
    }
}
