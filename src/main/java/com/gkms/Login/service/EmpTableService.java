package com.gkms.Login.service;

import com.choongang.erpproject.yslogin.repo.EmpTableRepository;
import com.choongang.erpproject.yslogin.ysdto.TokenInfo;
import com.choongang.erpproject.yslogin.ysjwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
//@Transactional(readOnly = true) //트랜잭션을 읽기전용으로 만들어서 잘못된 오입력을 막는다는데 이게 왜 안먹지..?
@RequiredArgsConstructor
public class EmpTableService {

    private final EmpTableRepository empTableRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String empId, String pw) {

        //1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(empId, pw);

        //2. 실제 검증(사용자 비밀번호 체크)이 이루어짐
        //authenticate 메서드 를 통해 요청된 인증에 대한 검증 실행하면서 CustomUserDetailService에서 만든 loadUserByUsername 메서드가 실행 (..네???)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //3. 인증 정보(Authentication 객체)를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;

    }

}
