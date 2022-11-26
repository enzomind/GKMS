package com.gkms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GkmsPageCont {

    @GetMapping("/")
    public String root(){
        return "/index";
    }



}
