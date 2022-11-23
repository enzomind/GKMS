package com.gkms.Login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageCont {

    @GetMapping("/login")
    public String loginRoot() {
        return "/login";
    }
}
