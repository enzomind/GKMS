package com.gkms.Login.config;

import com.choongang.erpproject.yslogin.ysFilter.JwtAuthenticationFilter;
import com.choongang.erpproject.yslogin.ysjwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Spring Security 설정을 위한 클래스
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //REST API라 basic auth 및 csrf 보안을 사용하지 않는다는 설정 (..네?)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT를 사용하기에 세션을 사용하지 않는다는 설정
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll() //해당 API에 대해 모든 요청을 허가한다는 설정 (예외처리겠지?)
                .antMatchers("/join", "/api/login", "/api/join", "/", "/error/*", "/css/**", "/**", "/js/**", "/etc/**","/users").permitAll()
//                .antMatchers("/acc/accounting").hasRole("USER") //임원이어야 요청할 수 있다는 설정(테스트로 일단 회계전표 줘본 상태)
                .anyRequest().authenticated() //이밖의 모든 요청에 대해 인증을 필요로 한다는 설정
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                //JWT 인증을 위해 직접 구현한 필터(JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter전에 실행하겠다는 설정
        return http.build();
    }

    //JWT를 사용하기위해선 기본적으로 Password encoder가 필요한데 여기선 Bycrypt encoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
