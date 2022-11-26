package com.gkms.login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;


    // authenticationManager를 Bean으로 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.httpBasic().disable()
                //일반적 루트가 아닌 다른 방식으로 요청 시 거절, header에 id/pw가 아닌 token(JWT)을 달고감.
                .authorizeRequests() //사용권한 체크
                .antMatchers("/test").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                //필터 등록 / 파라미터로 2개가 들어가는데 왼쪽은 커스텀한 필터링, 오른쪽에 등록한 필터 전, 커스텀 필터링이 수행됨.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); //Jwt필터를 UsernamePasswordAuthenticationFilter에 넣음

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //세션 사용하지 않도록 설정
    }

}
