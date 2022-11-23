package com.gkms.Login.controller;

import com.choongang.erpproject.yslogin.ysdto.LoginRequestDto;
import com.choongang.erpproject.yslogin.ysdto.TokenInfo;
import com.choongang.erpproject.yslogin.ysservice.EmpTableService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/login")
public class LoginApiCont {
    private final EmpTableService empTableService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginRequestDto loginRequestDto) {

        String empID = loginRequestDto.getEmpId();
        String pw = loginRequestDto.getPw();


        TokenInfo tokenInfo = empTableService.login(empID, pw);

        System.out.println("+++" + tokenInfo + "+++");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authoriztion","Bearer "+tokenInfo);

        return tokenInfo;
    }
}
